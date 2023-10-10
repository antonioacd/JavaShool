package com.javaschool.railway.transport.company.domain.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

  @Autowired
  private TicketRepository ticketRepository;

  @GetMapping
  public List<TicketEntity> getAllStations() {
    return ticketRepository.findAll();
  } 

  @GetMapping("/{id}")
  public TicketEntity getStationById(@PathVariable Long id) {
    return ticketRepository.findById(id).get();
  }

  @PostMapping
  public TicketEntity createStation(@RequestBody TicketEntity ticketEntity) {
    return ticketRepository.save(ticketEntity);
  }
}