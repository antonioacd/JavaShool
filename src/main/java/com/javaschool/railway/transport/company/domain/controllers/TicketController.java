package com.javaschool.railway.transport.company.domain.controllers;

import com.javaschool.railway.transport.company.domain.entitites.TicketEntity;
import com.javaschool.railway.transport.company.domain.infodto.TicketInfoDTO;
import com.javaschool.railway.transport.company.domain.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    /**
     * Creates a new ticket.
     *
     * @param ticketEntity The ticket entity to be created.
     * @return A DTO (Data Transfer Object) containing the ticket's information.
     */
    @PostMapping
    public TicketInfoDTO createTicket(@RequestBody TicketEntity ticketEntity) {
        // Delegate ticket creation logic to the TicketService and return the DTO
        return ticketService.createTicket(ticketEntity);
    }

    /**
     * Updates an existing ticket.
     *
     * @param id           The ID of the ticket to be updated.
     * @param updatedTicket The updated ticket DTO.
     * @return A DTO (Data Transfer Object) containing the updated ticket's information.
     */
    @PutMapping("/{id}")
    public TicketInfoDTO updateTicket(@PathVariable Long id, @RequestBody TicketInfoDTO updatedTicket) {
        // Delegate ticket update logic to the TicketService and return the DTO
        return ticketService.updateTicket(id, updatedTicket);
    }

    /**
     * Retrieves a list of all tickets.
     *
     * @return A list of DTOs containing ticket information.
     */
    @GetMapping
    public List<TicketInfoDTO> getAllTickets() {
        // Retrieve all tickets from the service and return the list of DTOs
        return ticketService.getAllTickets();
    }

    /**
     * Retrieves ticket information by its ID.
     *
     * @param id The ID of the ticket to retrieve.
     * @return A DTO containing the ticket's information.
     */
    @GetMapping("/{id}")
    public TicketInfoDTO getTicketById(@PathVariable Long id) {
        // Delegate ticket retrieval logic to the TicketService and return the DTO
        return ticketService.getTicketById(id);
    }

    /**
     * Deletes a ticket by its ID.
     *
     * @param id The ID of the ticket to be deleted.
     */
    @DeleteMapping("/{id}")
    public void deleteTicketById(@PathVariable Long id) {
        // Delegate ticket deletion logic to the TicketService
        ticketService.deleteTicketById(id);
    }

    /**
     * Searches for tickets based on user and schedule ID.
     *
     * @param userId     The ID of the user.
     * @param scheduleId The ID of the schedule.
     * @return ResponseEntity containing a list of ticket entities.
     */
    @GetMapping("/searchTicketsByUserAndScheduleId")
    public ResponseEntity<List<TicketEntity>> findTicketsByUser(
            @RequestParam(name = "userId", required = false) Long userId,
            @RequestParam(name = "scheduleId", required = false) Long scheduleId) {
        // Search for tickets based on the provided user and schedule IDs and return the ResponseEntity
        List<TicketEntity> tickets = ticketService.findTicketsByUserAndSchedule(userId, scheduleId);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("search/{scheduleId}")
    public List<TicketEntity> getTicketsByScheduleId(@PathVariable Long scheduleId) {
        return ticketService.getTicketsByScheduleId(scheduleId);
    }
}
