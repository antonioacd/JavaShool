package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.ScheduleEntity;
import com.javaschool.railway.transport.company.domain.entitites.TicketEntity;
import com.javaschool.railway.transport.company.domain.entitites.UserEntity;
import com.javaschool.railway.transport.company.domain.infodto.TicketInfoDTO;
import com.javaschool.railway.transport.company.domain.repositories.ScheduleRepository;
import com.javaschool.railway.transport.company.domain.repositories.TicketRepository;
import com.javaschool.railway.transport.company.domain.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing ticket-related operations.
 */
@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TicketService(TicketRepository ticketRepository, ScheduleRepository scheduleRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.ticketRepository = ticketRepository;
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Creates a new ticket and returns the ticket's information.
     *
     * @param ticket The ticket entity to be created.
     * @return A DTO (Data Transfer Object) containing the ticket's information.
     */
    public TicketInfoDTO createTicket(TicketEntity ticket) {
        ScheduleEntity schedule = scheduleRepository.getReferenceById(ticket.getSchedule().getId());
        UserEntity user = userRepository.getReferenceById(ticket.getUser().getId());

        // Check if the user already has a ticket for this schedule
        List<TicketEntity> userTicketsForSchedule = ticketRepository.findTicketsByUserIdAndScheduleId(user.getId(), schedule.getId());
        if (!userTicketsForSchedule.isEmpty()) {
            throw new IllegalStateException("The user already has a ticket for this train.");
        }

        // Check if there are available seats for this schedule
        if (schedule.getOccupiedSeats() >= schedule.getTrain().getSeats()) {
            throw new IllegalStateException("No available seats for this schedule.");
        }

        // Calculate minutes until departure
        Date departureDate = schedule.getDepartureTime();
        LocalDateTime departureTime = departureDate.toInstant().atZone(ZoneOffset.UTC).toLocalDateTime();
        LocalDateTime currentTime = LocalDateTime.now(ZoneOffset.UTC);
        long minutesUntilDeparture = ChronoUnit.MINUTES.between(currentTime, departureTime);

        // Check if it's within the allowed time frame for ticket purchase
        if (minutesUntilDeparture - 60 <= 10) {
            throw new IllegalStateException("Time exception");
        }

        // Assign the next available seat
        int nextAvailableSeat = schedule.getOccupiedSeats() + 1;
        ticket.setSeatNumber(nextAvailableSeat);
        schedule.setOccupiedSeats(nextAvailableSeat);

        // Save changes to schedule and ticket entities
        scheduleRepository.save(schedule);
        return modelMapper.map(ticketRepository.save(ticket), TicketInfoDTO.class);
    }

    /**
     * Updates a ticket entity by ID and returns the updated ticket's information.
     *
     * @param id              The ID of the ticket to be updated.
     * @param updatedTicket   The updated ticket entity.
     * @return A DTO (Data Transfer Object) containing the updated ticket's information.
     * @throws EntityNotFoundException If the ticket is not found.
     */
    public TicketInfoDTO updateTicket(Long id, TicketInfoDTO updatedTicket) {
        TicketEntity existingTicket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));

        // Update the ticket information
        existingTicket.setSeatNumber(updatedTicket.getSeatNumber());

        // Update the associated user (if different)
        if (!existingTicket.getUser().getId().equals(updatedTicket.getUser().getId())) {
            existingTicket.setUser(userRepository.getReferenceById(updatedTicket.getUser().getId()));
        }

        // Update the associated schedule (if different)
        if (!existingTicket.getSchedule().getId().equals(updatedTicket.getSchedule().getId())) {
            existingTicket.setSchedule(scheduleRepository.getReferenceById(updatedTicket.getSchedule().getId()));
        }

        // Save changes to the ticket entity
        return modelMapper.map(ticketRepository.save(existingTicket), TicketInfoDTO.class);
    }

    /**
     * Deletes a ticket by its ID.
     *
     * @param id The ID of the ticket to be deleted.
     */
    public void deleteTicketById(Long id) {
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
    public List<TicketEntity> findTicketsByUserAndSchedule(Long userId, Long scheduleId) {
        // Find tickets based on user ID and schedule ID
        return ticketRepository.findTicketsByUserIdAndScheduleId(userId, scheduleId);
    }
}
