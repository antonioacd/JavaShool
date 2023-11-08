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

  @PostMapping
  public TicketInfoDTO createTicket(@RequestBody TicketEntity ticketEntity) {
    return ticketService.createTicket(ticketEntity);
  }

  @PutMapping("/{id}")
  public TicketInfoDTO updateTicket(@PathVariable Long id, @RequestBody TicketInfoDTO updatedTicket) {
    return ticketService.updateTicket(id, updatedTicket);
  }

  @GetMapping
  public List<TicketInfoDTO> getAllTickets() {
    return ticketService.getAllTickets();
  }

  @GetMapping("/{id}")
  public TicketInfoDTO getTicketById(@PathVariable Long id) {
    return ticketService.getTicketById(id);
  }

  @GetMapping("/searchTicketsByUserAndScheduleId")
  public ResponseEntity<List<TicketEntity>> findTicketsByUser(
          @RequestParam(name = "userId", required = false) Long userId,
          @RequestParam(name = "scheduleId", required = false) Long scheduleId) {
    List<TicketEntity> tickets = ticketService.findTicketsByUserAndSchedule(userId, scheduleId);
    return ResponseEntity.ok(tickets);
  }


}