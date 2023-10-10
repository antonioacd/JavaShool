package com.javaschool.railway.transport.company.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
public class TicketController {

  @Autowired
  private TicketRepository ticketRepository;

  @GetMapping
  public List<Ticket> getAllStations() {
    return ticketRepository.findAll();
  } 

  @GetMapping("/{id}")
  public Ticket getStationById(@PathVariable Long id) {
    return ticketRepository.findById(id).get();
  }

  @PostMapping
  public Ticket createStation(@RequestBody Ticket ticket) {
    return ticketRepository.save(ticket);
  }
}