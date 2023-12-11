package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.ScheduleEntity;
import com.javaschool.railway.transport.company.domain.entitites.TicketEntity;
import com.javaschool.railway.transport.company.domain.entitites.UserEntity;
import com.javaschool.railway.transport.company.domain.infodto.TicketInfoDTO;
import com.javaschool.railway.transport.company.domain.repositories.ScheduleRepository;
import com.javaschool.railway.transport.company.domain.repositories.TicketRepository;
import com.javaschool.railway.transport.company.domain.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing ticket-related operations.
 */
@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    /**
     * Creates a new ticket and returns the ticket's information.
     *
     * @param ticket The ticket entity to be created.
     * @return A DTO (Data Transfer Object) containing the ticket's information.
     * @throws IllegalStateException If there are issues with ticket creation.
     */
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public TicketInfoDTO createTicket(TicketEntity ticket) {

        // Retrieve the schedule associated with the provided ticket
        ScheduleEntity schedule = scheduleRepository.findById(ticket.getSchedule().getId())
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));

        // Check if the schedule is departing in the next 10 minutes
        if (isDepartingSoon(schedule)) {
            throw new IllegalStateException("Cannot purchase a ticket for a schedule departing in the next 10 minutes.");
        }

        // Retrieve the user associated with the provided ticket
        UserEntity user = userRepository.findById(ticket.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Check if the user already has a ticket for this schedule
        List<TicketEntity> userTicketsForSchedule = ticketRepository.findTicketsByUserIdAndScheduleId(user.getId(), schedule.getId());
        if (!userTicketsForSchedule.isEmpty()) {
            throw new IllegalStateException("The user already has a ticket for this schedule.");
        }

        int totalSeats = schedule.getTrain().getSeats();

        // Iterate through available seats to find and assign an unoccupied seat
        for (int seatNumber = 1; seatNumber <= totalSeats; seatNumber++) {
            // Check if there is an existing ticket with this seat and schedule
            boolean seatOccupied = ticketRepository.existsByScheduleIdAndSeatNumber(schedule.getId(), seatNumber);

            if (!seatOccupied) {
                // Assign the seat and mark it as occupied
                schedule.setOccupiedSeats(schedule.getOccupiedSeats() + 1);
                scheduleRepository.save(schedule);

                // Create and save the new ticket
                TicketEntity newTicket = new TicketEntity();
                newTicket.setSeatNumber(seatNumber);
                newTicket.setUser(user);
                newTicket.setSchedule(schedule);
                return modelMapper.map(ticketRepository.save(newTicket), TicketInfoDTO.class);
            }
        }

        // If we reach this point, there are no available seats for this schedule
        throw new IllegalStateException("No available seats for this schedule.");
    }

    /**
     * Check if the schedule is departing in the next 10 minutes.
     *
     * @param schedule The schedule entity to be checked.
     * @return true if the schedule is departing soon, false otherwise.
     */
    private boolean isDepartingSoon(ScheduleEntity schedule) {
        // Use the Madrid time zone explicitly
        ZoneId madridZoneId = ZoneId.of("Europe/Madrid");

        // Get the current time in the Madrid time zone
        LocalDateTime now = LocalDateTime.now(madridZoneId);

        // Convert the departure date to LocalDateTime in the Madrid time zone and then to UTC
        LocalDateTime departureTimeUTC = schedule.getDepartureTime()
                .toInstant()
                .atZone(madridZoneId)
                .withZoneSameInstant(ZoneOffset.UTC)
                .toLocalDateTime();

        // Check if the schedule is departing in the next 10 minutes
        return now.plusMinutes(10).isAfter(departureTimeUTC);
    }

    /**
     * Deletes a ticket by its ID.
     *
     * @param id The ID of the ticket to be deleted.
     * @throws EntityNotFoundException If the ticket is not found.
     */
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public void deleteTicketById(Long id) {
        // Find the ticket by ID or throw an exception if not found
        TicketEntity ticketEntity = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));

        // Remove the ticket's association with the schedule
        ScheduleEntity schedule = ticketEntity.getSchedule();
        if (schedule != null) {
            schedule.setOccupiedSeats(schedule.getOccupiedSeats() - 1);
            scheduleRepository.save(schedule);
        }

        // Delete the ticket by ID
        ticketRepository.deleteById(id);
    }

    /**
     * Retrieves ticket information by its ID.
     *
     * @param id The ID of the ticket to retrieve.
     * @return A DTO containing the ticket's information.
     * @throws EntityNotFoundException If the ticket is not found.
     */
    @Secured({"ROLE_ADMIN"})
    public TicketInfoDTO getTicketById(Long id) {
        // Find the ticket by ID or throw an exception if not found
        TicketEntity ticketEntity = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
        // Map the ticket entity to a DTO
        return modelMapper.map(ticketEntity, TicketInfoDTO.class);
    }

    /**
     * Retrieves a list of all tickets.
     *
     * @return A list of DTOs containing ticket information.
     */
    @Secured({"ROLE_ADMIN"})
    public List<TicketInfoDTO> getAllTickets() {
        // Retrieve all tickets from the repository
        List<TicketEntity> tickets = ticketRepository.findAll();
        // Map each ticket entity to a DTO and collect into a list
        return tickets.stream()
                .map(ticket -> modelMapper.map(ticket, TicketInfoDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of tickets based on user ID and schedule ID.
     *
     * @param userId     The ID of the user.
     * @param scheduleId The ID of the schedule.
     * @return A list of ticket entities that match the criteria.
     */
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public List<TicketEntity> findTicketsByUserAndSchedule(Long userId, Long scheduleId) {
        // Find tickets based on user ID and schedule ID
        return ticketRepository.findTicketsByUserIdAndScheduleId(userId, scheduleId);
    }

    /**
     * Retrieves a list of tickets based on schedule ID.
     *
     * @param scheduleId The ID of the schedule.
     * @return A list of ticket entities that match the schedule ID.
     */
    @Secured({"ROLE_ADMIN"})
    public List<TicketEntity> getTicketsByScheduleId(Long scheduleId) {
        return ticketRepository.findTicketsByScheduleId(scheduleId);
    }
}
