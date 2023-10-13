package com.javaschool.railway.transport.company.domain.controllers;

import com.javaschool.railway.transport.company.domain.entitites.TicketEntity;
import com.javaschool.railway.transport.company.domain.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

  @Autowired
  private TicketService ticketService;

  @PostMapping
  public TicketEntity createTicket(@RequestBody TicketEntity ticketEntity) {
    return ticketService.createTicket(ticketEntity);
  }

  @GetMapping
  public List<TicketEntity> getAllTickets() {
    return ticketService.findAll();
  } 

  @GetMapping("/{id}")
  public TicketEntity getTicketById(@PathVariable Long id) {
    return ticketService.getTicketById(id);
  }


}