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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public TicketInfoDTO createTicket(TicketEntity ticket) {

        ScheduleEntity schedule = scheduleRepository.findById(ticket.getSchedule().getId())
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));

        UserEntity user = userRepository.findById(ticket.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Verificar si el usuario ya tiene un ticket para este horario
        List<TicketEntity> userTicketsForSchedule = ticketRepository.findTicketsByUserIdAndScheduleId(user.getId(), schedule.getId());
        if (!userTicketsForSchedule.isEmpty()) {
            throw new IllegalStateException("The user already has a ticket for this schedule.");
        }

        int totalSeats = schedule.getTrain().getSeats();

        for (int seatNumber = 1; seatNumber <= totalSeats; seatNumber++) {
            // Verificar si hay un ticket existente con este asiento y horario
            boolean seatOccupied = ticketRepository.existsByScheduleIdAndSeatNumber(schedule.getId(), seatNumber);

            if (!seatOccupied) {
                // Asignar el asiento y marcarlo como ocupado
                schedule.setOccupiedSeats(schedule.getOccupiedSeats() + 1);
                scheduleRepository.save(schedule);

                System.out.println("Ha encontrado un sitio libre: " + seatNumber);

                // Crear y guardar el ticket
                TicketEntity newTicket = new TicketEntity();
                newTicket.setSeatNumber(seatNumber);
                newTicket.setUser(user);
                newTicket.setSchedule(schedule);
                return modelMapper.map(ticketRepository.save(newTicket), TicketInfoDTO.class);
            }
        }

        // Si llegamos aquí, no hay asientos disponibles
        throw new IllegalStateException("No available seats for this schedule.");
    }

    /**
     * Updates a ticket entity by ID and returns the updated ticket's information.
     *
     * @param id            The ID of the ticket to be updated.
     * @param updatedTicket The updated ticket entity.
     * @return A DTO containing the updated ticket's information.
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
     * @throws EntityNotFoundException If the ticket is not found.
     */
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

    /**
     * Retrieves a list of tickets based on schedule ID.
     *
     * @param scheduleId The ID of the schedule.
     * @return A list of ticket entities that match the schedule ID.
     */
    public List<TicketEntity> getTicketsByScheduleId(Long scheduleId) {
        return ticketRepository.findTicketsByScheduleId(scheduleId);
    }
}
