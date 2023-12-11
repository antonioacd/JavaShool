package com.javaschool.railway.transport.company.domain.controllers;

import com.javaschool.railway.transport.company.domain.entitites.TicketEntity;
import com.javaschool.railway.transport.company.domain.infodto.TicketInfoDTO;
import com.javaschool.railway.transport.company.domain.services.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/tickets")
@AllArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    /**
     * Creates a new ticket.
     *
     * @param ticketEntity The ticket entity to be created.
     * @return A DTO (Data Transfer Object) containing the ticket's information.
     */
    @PostMapping
    public TicketInfoDTO createTicket(@RequestBody TicketEntity ticketEntity) {
        return ticketService.createTicket(ticketEntity);
    }

    /**
     * Retrieves a list of all tickets.
     *
     * @return A list of DTOs containing ticket information.
     */
    @GetMapping
    public List<TicketInfoDTO> getAllTickets() {
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
        return ticketService.getTicketById(id);
    }

    /**
     * Deletes a ticket by its ID.
     *
     * @param id The ID of the ticket to be deleted.
     */
    @DeleteMapping("/{id}")
    public void deleteTicketById(@PathVariable Long id) {
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
        List<TicketEntity> tickets = ticketService.findTicketsByUserAndSchedule(userId, scheduleId);
        return ResponseEntity.ok(tickets);
    }

    /**
     * Retrieves a list of tickets based on schedule ID.
     *
     * @param scheduleId The ID of the schedule.
     * @return A list of ticket entities.
     */
    @GetMapping("search/{scheduleId}")
    public List<TicketEntity> getTicketsByScheduleId(@PathVariable Long scheduleId) {
        return ticketService.getTicketsByScheduleId(scheduleId);
    }
}
