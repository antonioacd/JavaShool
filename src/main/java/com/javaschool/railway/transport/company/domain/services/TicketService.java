package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.TicketEntity;
import com.javaschool.railway.transport.company.domain.repositories.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketEntity createTicket(TicketEntity ticket){
        return ticketRepository.save(ticket);
    }

    public TicketEntity getTicketById(Long id) {
        return ticketRepository.findById(id).get();
    }

    public List<TicketEntity> findAll(){
        return ticketRepository.findAll();
    }

}