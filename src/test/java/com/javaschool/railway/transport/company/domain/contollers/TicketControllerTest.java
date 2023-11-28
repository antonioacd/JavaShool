package com.javaschool.railway.transport.company.domain.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaschool.railway.transport.company.domain.entitites.TicketEntity;
import com.javaschool.railway.transport.company.domain.infodto.TicketInfoDTO;
import com.javaschool.railway.transport.company.domain.services.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @Autowired
    private ObjectMapper objectMapper;

    private TicketInfoDTO ticketInfoDTO;
    private TicketEntity ticketEntity;

    /**
     * Set up common data for tests.
     */
    @BeforeEach
    public void setUp() {
        ticketEntity = TicketEntity.builder().id(1L).seatNumber(1).build();
        ticketInfoDTO = TicketInfoDTO.builder().id(1L).seatNumber(1).build();
    }

    /**
     * Test case for creating a new ticket.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    public void createTicket_ReturnCreated() throws Exception {
        // Arrange
        given(ticketService.createTicket(ticketEntity)).willReturn(ticketInfoDTO);

        // Act and Assert
        mockMvc.perform(post("/api/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticketEntity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.seatNumber", is(1)));
    }

    /**
     * Test case for retrieving a list of all tickets.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    public void getAllTickets_ReturnListOfTickets() throws Exception {
        // Arrange
        List<TicketInfoDTO> ticketList = Arrays.asList(
                TicketInfoDTO.builder().id(1L).seatNumber(1).build(),
                TicketInfoDTO.builder().id(2L).seatNumber(2).build()
        );

        given(ticketService.getAllTickets()).willReturn(ticketList);

        // Act and Assert
        mockMvc.perform(get("/api/tickets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].seatNumber", is(1)))
                .andExpect(jsonPath("$[1].seatNumber", is(2)));
    }

    /**
     * Test case for retrieving ticket information by ID.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    public void getTicketById_ReturnTicket() throws Exception {
        // Arrange
        Long ticketId = 1L;
        given(ticketService.getTicketById(ticketId)).willReturn(ticketInfoDTO);

        // Act and Assert
        mockMvc.perform(get("/api/tickets/{id}", ticketId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.seatNumber", is(1)));
    }

    /**
     * Test case for deleting a ticket by ID.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    public void deleteTicketById_ReturnNoContent() throws Exception {
        // Arrange
        Long ticketId = 1L;

        // Act and Assert
        mockMvc.perform(delete("/api/tickets/{id}", ticketId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test case for searching tickets by user and schedule ID.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    public void searchTicketsByUserAndSchedule_ReturnListOfTickets() throws Exception {
        // Arrange
        Long userId = 1L;
        Long scheduleId = 1L;
        List<TicketEntity> ticketList = Arrays.asList(
                TicketEntity.builder().id(1L).seatNumber(1).build(),
                TicketEntity.builder().id(2L).seatNumber(2).build()
        );

        given(ticketService.findTicketsByUserAndSchedule(userId, scheduleId)).willReturn(ticketList);

        // Act and Assert
        ResultActions resultActions = mockMvc.perform(get("/api/tickets/searchTicketsByUserAndScheduleId")
                        .param("userId", userId.toString())
                        .param("scheduleId", scheduleId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Here you can add further assertions based on your specific response structure.
        // For example, you can check the size of the returned list, values of specific fields, etc.
    }

    /**
     * Test case for getting tickets by schedule ID.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    public void getTicketsByScheduleId_ReturnListOfTickets() throws Exception {
        // Arrange
        Long scheduleId = 1L;
        List<TicketEntity> ticketList = Arrays.asList(
                TicketEntity.builder().id(1L).seatNumber(1).build(),
                TicketEntity.builder().id(2L).seatNumber(2).build()
        );

        given(ticketService.getTicketsByScheduleId(scheduleId)).willReturn(ticketList);

        // Act and Assert
        ResultActions resultActions = mockMvc.perform(get("/api/tickets/search/{scheduleId}", scheduleId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Here you can add further assertions based on your specific response structure.
        // For example, you can check the size of the returned list, values of specific fields, etc.
    }
}
