package com.javaschool.railway.transport.company.domain.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

  @Autowired
  private TicketService ticketService;

  @PostMapping
  public TicketEntity createStation(@RequestBody TicketEntity ticketEntity) {
    return ticketService.createTicket(ticketEntity);
  }

  @GetMapping
  public List<TicketEntity> getAllStations() {
    return ticketService.findAll();
  } 

  @GetMapping("/{id}")
  public TicketEntity getStationById(@PathVariable Long id) {
    return ticketService.getTicketById(id);
  }

}