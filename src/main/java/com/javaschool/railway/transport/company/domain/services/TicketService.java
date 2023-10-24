package com.javaschool.railway.transport.company.domain.services;
import com.javaschool.railway.transport.company.domain.entitites.TicketEntity;
import com.javaschool.railway.transport.company.domain.infodto.TicketInfoDTO;
import com.javaschool.railway.transport.company.domain.repositories.TicketRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TicketService {
    @Autowired
    private final TicketRepository ticketRepository;
    @Autowired
    private final ModelMapper modelMapper;

    public TicketEntity createTicket(TicketEntity ticket){
        return ticketRepository.save(ticket);
    }

    public TicketEntity getTicketById(Long id) {
        return ticketRepository.findById(id).get();
    }

    public List<TicketInfoDTO> getAllTickets() {
        List<TicketEntity> tickets = ticketRepository.findAll();

        return tickets.stream()
                .map(ticket -> modelMapper.map(ticket, TicketInfoDTO.class))
                .collect(Collectors.toList());
    }

}