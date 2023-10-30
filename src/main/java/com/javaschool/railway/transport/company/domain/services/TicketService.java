package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.TicketEntity;
import com.javaschool.railway.transport.company.domain.infodto.TicketInfoDTO;
import com.javaschool.railway.transport.company.domain.repositories.ScheduleRepository;
import com.javaschool.railway.transport.company.domain.repositories.TicketRepository;
import com.javaschool.railway.transport.company.domain.repositories.UserRepository;
import com.javaschool.railway.transport.company.domain.repositories.UserRepositoryJWT;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        ticket.setSchedule(scheduleRepository.getReferenceById(ticket.getSchedule().getId()));
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
}
