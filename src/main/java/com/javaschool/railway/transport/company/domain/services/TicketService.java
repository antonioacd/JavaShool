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
@AllArgsConstructor
public class TicketService {

    @Autowired
    private final TicketRepository ticketRepository;
    @Autowired
    private final ScheduleRepository scheduleRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ModelMapper modelMapper;

    /**
     * Creates a new ticket and returns the ticket's information.
     *
     * @param ticket The ticket entity to be created.
     * @return A DTO (Data Transfer Object) containing the ticket's information.
     */
    public TicketInfoDTO createTicket(TicketEntity ticket) {
        ScheduleEntity schedule = scheduleRepository.getReferenceById(ticket.getSchedule().getId());
        UserEntity user = userRepository.getReferenceById(ticket.getUser().getId());

        List<TicketEntity> userTicketsForSchedule = ticketRepository.findTicketsByUserIdAndScheduleId(user.getId(), schedule.getId());
        if (!userTicketsForSchedule.isEmpty()) {
            throw new IllegalStateException("El usuario ya tiene un billete para este tren.");
        }

        if (schedule.getOccupiedSeats() >= schedule.getTrain().getSeats()) {
            throw new IllegalStateException("No hay asientos disponibles para este horario.");
        }

        Date departureDate = schedule.getDepartureTime();
        LocalDateTime departureTime = departureDate.toInstant().atZone(ZoneOffset.UTC).toLocalDateTime();

        LocalDateTime currentTime = LocalDateTime.now(ZoneOffset.UTC);

        long minutesUntilDeparture = ChronoUnit.MINUTES.between(currentTime, departureTime);

        System.out.println("Departure: " + departureTime.toString());
        System.out.println("Current: " + currentTime.toString());
        System.out.println("Minutes until departure: " + minutesUntilDeparture);

        if (minutesUntilDeparture-60 <= 10) {
            throw new IllegalStateException("Time exception");
        }

        int nextAvailableSeat = schedule.getOccupiedSeats() + 1;

        ticket.setSeatNumber(nextAvailableSeat);
        schedule.setOccupiedSeats(nextAvailableSeat);

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

        return modelMapper.map(ticketRepository.save(existingTicket), TicketInfoDTO.class);
    }


    /**
     * Deletes a ticket by its ID.
     *
     * @param id The ID of the ticket to be deleted.
     */
    public void deleteTicketById(Long id) {
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
        TicketEntity ticketEntity = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
        return modelMapper.map(ticketEntity, TicketInfoDTO.class);
    }

    /**
     * Retrieves a list of all tickets.
     *
     * @return A list of DTOs containing ticket information.
     */
    public List<TicketInfoDTO> getAllTickets() {
        List<TicketEntity> tickets = ticketRepository.findAll();

        return tickets.stream()
                .map(ticket -> modelMapper.map(ticket, TicketInfoDTO.class))
                .collect(Collectors.toList());
    }

    public List<TicketEntity> findTicketsByUserAndSchedule(Long userId, Long scheduleId) {

        return ticketRepository.findTicketsByUserIdAndScheduleId(userId, scheduleId);
    }
}
