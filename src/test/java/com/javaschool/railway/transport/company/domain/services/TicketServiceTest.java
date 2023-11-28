package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.ScheduleEntity;
import com.javaschool.railway.transport.company.domain.entitites.TicketEntity;
import com.javaschool.railway.transport.company.domain.entitites.TrainEntity;
import com.javaschool.railway.transport.company.domain.entitites.UserEntity;
import com.javaschool.railway.transport.company.domain.infodto.ScheduleInfoDTO;
import com.javaschool.railway.transport.company.domain.infodto.TicketInfoDTO;
import com.javaschool.railway.transport.company.domain.infodto.UserInfoDTO;
import com.javaschool.railway.transport.company.domain.repositories.ScheduleRepository;
import com.javaschool.railway.transport.company.domain.repositories.TicketRepository;
import com.javaschool.railway.transport.company.domain.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the TicketService class.
 */
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test for the createTicket method when the ticket creation is successful.
     */
    @Test
    void createTicket_Successful() {
        // Arrange
        ScheduleEntity schedule = ScheduleEntity.builder()
                .id(1L)
                .departureTime(new Date())
                .arrivalTime(new Date())
                .train(TrainEntity.builder().id(1L).seats(5).build())
                .build();

        UserEntity user = UserEntity.builder()
                .id(1L)
                .email("test@example.com")
                .password("password")
                .build();

        TicketEntity ticket = TicketEntity.builder()
                .seatNumber(1)
                .user(user)
                .schedule(schedule)
                .build();

        TicketInfoDTO ticketDTO = TicketInfoDTO.builder()
                .seatNumber(1)
                .user(UserInfoDTO.builder().id(1L).email("test@example.com").build())
                .schedule(ScheduleInfoDTO.builder().id(1L).build())
                .build();

        // Mocking necessary repository methods
        when(scheduleRepository.findById(anyLong())).thenReturn(java.util.Optional.of(schedule));
        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.of(user));
        when(ticketRepository.findTicketsByUserIdAndScheduleId(anyLong(), anyLong())).thenReturn(new ArrayList<>());
        when(ticketRepository.existsByScheduleIdAndSeatNumber(anyLong(), anyInt())).thenReturn(false);
        when(modelMapper.map(any(), eq(TicketInfoDTO.class))).thenReturn(ticketDTO);
        when(ticketRepository.save(any())).thenReturn(ticket);

        // Act
        TicketInfoDTO createdTicket = ticketService.createTicket(ticket);

        // Assert
        assertThat(createdTicket).isNotNull();
        assertThat(createdTicket.getSeatNumber()).isEqualTo(ticketDTO.getSeatNumber());
        assertThat(createdTicket.getUser().getId()).isEqualTo(ticketDTO.getUser().getId());
        assertThat(createdTicket.getSchedule().getId()).isEqualTo(ticketDTO.getSchedule().getId());
    }

    /**
     * Test for the createTicket method when the user already has a ticket for the given schedule.
     * An exception should be thrown in this case.
     */
    @Test
    void createTicket_UserAlreadyHasTicketForSchedule_ExceptionThrown() {
        // Arrange
        ScheduleEntity schedule = ScheduleEntity.builder()
                .id(1L)
                .departureTime(new Date())
                .arrivalTime(new Date())
                .train(TrainEntity.builder().id(1L).seats(5).build())
                .build();

        UserEntity user = UserEntity.builder()
                .id(1L)
                .email("test@example.com")
                .password("password")
                .build();

        TicketEntity existingTicket = TicketEntity.builder()
                .seatNumber(2)
                .user(user)
                .schedule(schedule)
                .build();

        List<TicketEntity> existingTickets = List.of(existingTicket);

        // Mocking necessary repository methods
        when(scheduleRepository.findById(anyLong())).thenReturn(java.util.Optional.of(schedule));
        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.of(user));
        when(ticketRepository.findTicketsByUserIdAndScheduleId(anyLong(), anyLong())).thenReturn(existingTickets);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> ticketService.createTicket(existingTicket));
    }

    /**
     * Test for the createTicket method when there are no available seats for the given schedule.
     * An exception should be thrown in this case.
     */
    @Test
    void createTicket_NoAvailableSeats_ExceptionThrown() {
        // Arrange
        ScheduleEntity schedule = ScheduleEntity.builder()
                .id(1L)
                .departureTime(new Date())
                .arrivalTime(new Date())
                .train(TrainEntity.builder().id(1L).seats(1).build())
                .build();

        UserEntity user = UserEntity.builder()
                .id(1L)
                .email("test@example.com")
                .password("password")
                .build();

        TicketEntity existingTicket = TicketEntity.builder()
                .seatNumber(1)
                .user(user)
                .schedule(schedule)
                .build();

        List<TicketEntity> existingTickets = List.of(existingTicket);

        // Mocking necessary repository methods
        when(scheduleRepository.findById(anyLong())).thenReturn(java.util.Optional.of(schedule));
        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.of(user));
        when(ticketRepository.findTicketsByUserIdAndScheduleId(anyLong(), anyLong())).thenReturn(existingTickets);
        when(ticketRepository.existsByScheduleIdAndSeatNumber(anyLong(), anyInt())).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> ticketService.createTicket(existingTicket));
    }

    /**
     * Test for the deleteTicketById method when the ticket deletion is successful.
     */
    @Test
    void deleteTicketById_Successful() {
        // Arrange
        Long ticketId = 1L;
        TicketEntity existingTicket = TicketEntity.builder()
                .id(ticketId)
                .seatNumber(1)
                .user(UserEntity.builder().id(1L).build())
                .schedule(ScheduleEntity.builder().id(1L).occupiedSeats(1).build())
                .build();

        // Mocking necessary repository methods
        when(ticketRepository.findById(ticketId)).thenReturn(java.util.Optional.of(existingTicket));

        // Act
        ticketService.deleteTicketById(ticketId);

        // Assert
        verify(scheduleRepository, times(1)).save(any(ScheduleEntity.class));
        verify(ticketRepository, times(1)).deleteById(ticketId);
    }

    /**
     * Test for the getTicketById method when the ticket retrieval is successful.
     */
    @Test
    void getTicketById_Successful() {
        // Arrange
        Long ticketId = 1L;
        TicketEntity existingTicket = TicketEntity.builder()
                .id(ticketId)
                .seatNumber(1)
                .user(UserEntity.builder().id(1L).build())
                .schedule(ScheduleEntity.builder().id(1L).build())
                .build();

        TicketInfoDTO ticketDTO = TicketInfoDTO.builder()
                .id(ticketId)
                .seatNumber(1)
                .user(UserInfoDTO.builder().id(1L).build())
                .schedule(ScheduleInfoDTO.builder().id(1L).build())
                .build();

        // Mocking necessary repository methods
        when(ticketRepository.findById(ticketId)).thenReturn(java.util.Optional.of(existingTicket));
        when(modelMapper.map(any(), eq(TicketInfoDTO.class))).thenReturn(ticketDTO);

        // Act
        TicketInfoDTO retrievedTicket = ticketService.getTicketById(ticketId);

        // Assert
        assertThat(retrievedTicket).isNotNull();
        assertThat(retrievedTicket.getId()).isEqualTo(ticketDTO.getId());
        assertThat(retrievedTicket.getSeatNumber()).isEqualTo(ticketDTO.getSeatNumber());
        assertThat(retrievedTicket.getUser().getId()).isEqualTo(ticketDTO.getUser().getId());
        assertThat(retrievedTicket.getSchedule().getId()).isEqualTo(ticketDTO.getSchedule().getId());
    }

    /**
     * Test for the getAllTickets method when retrieving all tickets is successful.
     */
    @Test
    void getAllTickets_Successful() {
        // Arrange
        List<TicketEntity> ticketEntities = List.of(
                TicketEntity.builder().id(1L).seatNumber(1).build(),
                TicketEntity.builder().id(2L).seatNumber(2).build()
        );

        List<TicketInfoDTO> ticketDTOs = List.of(
                TicketInfoDTO.builder().id(1L).seatNumber(1).build(),
                TicketInfoDTO.builder().id(2L).seatNumber(2).build()
        );

        // Mocking necessary repository methods
        when(ticketRepository.findAll()).thenReturn(ticketEntities);
        when(modelMapper.map(any(), eq(TicketInfoDTO.class))).thenReturn(ticketDTOs.get(0), ticketDTOs.get(1));

        // Act
        List<TicketInfoDTO> allTickets = ticketService.getAllTickets();

        // Assert
        assertThat(allTickets).isNotNull();
        assertThat(allTickets.size()).isEqualTo(2);
        assertThat(allTickets.get(0).getId()).isEqualTo(ticketDTOs.get(0).getId());
        assertThat(allTickets.get(1).getId()).isEqualTo(ticketDTOs.get(1).getId());
    }

    /**
     * Test for the findTicketsByUserAndSchedule method when finding tickets is successful.
     */
    @Test
    void findTicketsByUserAndSchedule_Successful() {
        // Arrange
        Long userId = 1L;
        Long scheduleId = 1L;

        List<TicketEntity> ticketEntities = List.of(
                TicketEntity.builder().id(1L).seatNumber(1).build(),
                TicketEntity.builder().id(2L).seatNumber(2).build()
        );

        // Mocking necessary repository methods
        when(ticketRepository.findTicketsByUserIdAndScheduleId(userId, scheduleId)).thenReturn(ticketEntities);

        // Act
        List<TicketEntity> tickets = ticketService.findTicketsByUserAndSchedule(userId, scheduleId);

        // Assert
        assertThat(tickets).isNotNull();
        assertThat(tickets.size()).isEqualTo(2);
        assertThat(tickets.get(0).getId()).isEqualTo(1L);
        assertThat(tickets.get(1).getId()).isEqualTo(2L);
    }

    /**
     * Test for the getTicketsByScheduleId method when retrieving tickets by schedule ID is successful.
     */
    @Test
    void getTicketsByScheduleId_Successful() {
        // Arrange
        Long scheduleId = 1L;

        List<TicketEntity> ticketEntities = List.of(
                TicketEntity.builder().id(1L).seatNumber(1).build(),
                TicketEntity.builder().id(2L).seatNumber(2).build()
        );

        // Mocking necessary repository methods
        when(ticketRepository.findTicketsByScheduleId(scheduleId)).thenReturn(ticketEntities);

        // Act
        List<TicketEntity> tickets = ticketService.getTicketsByScheduleId(scheduleId);

        // Assert
        assertThat(tickets).isNotNull();
        assertThat(tickets.size()).isEqualTo(2);
        assertThat(tickets.get(0).getId()).isEqualTo(1L);
        assertThat(tickets.get(1).getId()).isEqualTo(2L);
    }

}
