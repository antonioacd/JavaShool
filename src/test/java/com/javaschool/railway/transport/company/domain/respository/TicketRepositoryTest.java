package com.javaschool.railway.transport.company.domain.respository;

import com.javaschool.railway.transport.company.domain.entitites.*;
import com.javaschool.railway.transport.company.domain.repositories.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Duration;
import java.util.Date;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Test
    public void TicketRepository_FindTicketsByUserIdAndScheduleId_ReturnMatchingTickets() {
        // Create roles
        RoleEntity role = RoleEntity.builder().name("USER").build();
        RoleEntity adminRole = RoleEntity.builder().name("ADMIN").build();

        // Save roles
        roleRepository.save(role);
        roleRepository.save(adminRole);

        // Create user with roles
        UserEntity user = UserEntity.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .password("password")
                .roles(List.of(role, adminRole))
                .build();

        // Save user
        user = userRepository.save(user);

        // Create stations
        StationEntity departureStation = StationEntity.builder().name("Station 1").city("City 1").build();
        StationEntity arrivalStation = StationEntity.builder().name("Station 2").city("City 2").build();

        // Save stations
        departureStation = stationRepository.save(departureStation);
        arrivalStation = stationRepository.save(arrivalStation);

        // Create train
        TrainEntity train = TrainEntity.builder()
                .seats(50)
                .duration(Duration.ofHours(2))
                .trainNumber("123")
                .departureStation(departureStation)
                .arrivalStation(arrivalStation)
                .build();

        // Save train
        train = trainRepository.save(train);

        // Create schedule
        ScheduleEntity schedule = ScheduleEntity.builder()
                .departureTime(new Date())
                .arrivalTime(new Date())
                .occupiedSeats(0)
                .train(train)
                .build();

        // Save schedule
        schedule = scheduleRepository.save(schedule);

        // Create ticket linked to user and schedule
        TicketEntity ticket = TicketEntity.builder()
                .seatNumber(1)
                .user(user)
                .schedule(schedule)
                .build();

        // Save ticket
        ticket = ticketRepository.save(ticket);

        // Find tickets by user ID and schedule ID
        List<TicketEntity> foundTickets = ticketRepository.findTicketsByUserIdAndScheduleId(user.getId(), schedule.getId());

        // Verify that the list is not null and contains one ticket
        Assertions.assertThat(foundTickets).isNotNull();
        Assertions.assertThat(foundTickets.size()).isEqualTo(1);
        Assertions.assertThat(foundTickets.get(0).getId()).isEqualTo(ticket.getId());
        Assertions.assertThat(foundTickets.get(0).getUser().getId()).isEqualTo(user.getId());
        Assertions.assertThat(foundTickets.get(0).getSchedule().getId()).isEqualTo(schedule.getId());
    }

    @Test
    public void TicketRepository_ExistsByScheduleIdAndSeatNumber_ReturnTrueForExistingTicket() {
        // Create roles
        RoleEntity role = RoleEntity.builder().name("USER").build();
        RoleEntity adminRole = RoleEntity.builder().name("ADMIN").build();

        // Save roles
        roleRepository.save(role);
        roleRepository.save(adminRole);

        // Create user with roles
        UserEntity user = UserEntity.builder()
                .name("Jane")
                .surname("Doe")
                .email("jane.doe@example.com")
                .password("password")
                .roles(List.of(role, adminRole))
                .build();

        // Save user
        user = userRepository.save(user);

        // Create stations
        StationEntity departureStation = StationEntity.builder().name("Station 5").city("City 5").build();
        StationEntity arrivalStation = StationEntity.builder().name("Station 6").city("City 6").build();

        // Save stations
        departureStation = stationRepository.save(departureStation);
        arrivalStation = stationRepository.save(arrivalStation);

        // Create train
        TrainEntity train = TrainEntity.builder()
                .seats(50)
                .duration(Duration.ofHours(4))
                .trainNumber("789")
                .departureStation(departureStation)
                .arrivalStation(arrivalStation)
                .build();

        // Save train
        train = trainRepository.save(train);

        // Create schedule
        ScheduleEntity schedule = ScheduleEntity.builder()
                .departureTime(new Date())
                .arrivalTime(new Date())
                .occupiedSeats(0)
                .train(train)
                .build();

        // Save schedule
        schedule = scheduleRepository.save(schedule);

        // Create ticket linked to user, schedule, and roles
        TicketEntity ticket = TicketEntity.builder()
                .seatNumber(3)
                .user(user)
                .schedule(schedule)
                .build();

        // Save ticket
        ticket = ticketRepository.save(ticket);

        // Check if ticket exists for the given schedule and seat number
        boolean exists = ticketRepository.existsByScheduleIdAndSeatNumber(schedule.getId(), ticket.getSeatNumber());

        // Verify that the ticket exists
        Assertions.assertThat(exists).isTrue();
    }

    @Test
    public void TicketRepository_ExistsByScheduleIdAndSeatNumber_ReturnFalseForNonExistingTicket() {
        // Create roles
        RoleEntity role = RoleEntity.builder().name("USER").build();
        RoleEntity adminRole = RoleEntity.builder().name("ADMIN").build();

        // Save roles
        roleRepository.save(role);
        roleRepository.save(adminRole);

        // Create user with roles
        UserEntity user = UserEntity.builder()
                .name("Bob")
                .surname("Smith")
                .email("bob.smith@example.com")
                .password("password")
                .roles(List.of(role, adminRole))
                .build();

        // Save user
        user = userRepository.save(user);

        // Create stations
        StationEntity departureStation = StationEntity.builder().name("Station 7").city("City 7").build();
        StationEntity arrivalStation = StationEntity.builder().name("Station 8").city("City 8").build();

        // Save stations
        departureStation = stationRepository.save(departureStation);
        arrivalStation = stationRepository.save(arrivalStation);

        // Create train
        TrainEntity train = TrainEntity.builder()
                .seats(50)
                .duration(Duration.ofHours(5))
                .trainNumber("101")
                .departureStation(departureStation)
                .arrivalStation(arrivalStation)
                .build();

        // Save train
        train = trainRepository.save(train);

        // Create schedule
        ScheduleEntity schedule = ScheduleEntity.builder()
                .departureTime(new Date())
                .arrivalTime(new Date())
                .occupiedSeats(0)
                .train(train)
                .build();

        // Save schedule
        schedule = scheduleRepository.save(schedule);

        // Create ticket linked to user, schedule, and roles
        TicketEntity ticket = TicketEntity.builder()
                .seatNumber(3)
                .user(user)
                .schedule(schedule)
                .build();

        // Save ticket
        ticket = ticketRepository.save(ticket);

        // Check if ticket exists for a non-existing seat number
        boolean exists = ticketRepository.existsByScheduleIdAndSeatNumber(schedule.getId(), 99);

        // Verify that the ticket does not exist
        Assertions.assertThat(exists).isFalse();
    }

    @Test
    public void TicketRepository_SaveTicket_ReturnSavedTicket() {
        // Create roles
        RoleEntity role = RoleEntity.builder().name("USER").build();
        RoleEntity adminRole = RoleEntity.builder().name("ADMIN").build();

        // Save roles
        roleRepository.save(role);
        roleRepository.save(adminRole);

        // Create user with roles
        UserEntity user = UserEntity.builder()
                .name("Alice")
                .surname("Johnson")
                .email("alice.johnson@example.com")
                .password("password")
                .roles(List.of(role, adminRole))
                .build();

        // Save user
        user = userRepository.save(user);

        // Create stations
        StationEntity departureStation = StationEntity.builder().name("Station 9").city("City 9").build();
        StationEntity arrivalStation = StationEntity.builder().name("Station 10").city("City 10").build();

        // Save stations
        departureStation = stationRepository.save(departureStation);
        arrivalStation = stationRepository.save(arrivalStation);

        // Create train
        TrainEntity train = TrainEntity.builder()
                .seats(50)
                .duration(Duration.ofHours(6))
                .trainNumber("112")
                .departureStation(departureStation)
                .arrivalStation(arrivalStation)
                .build();

        // Save train
        train = trainRepository.save(train);

        // Create schedule
        ScheduleEntity schedule = ScheduleEntity.builder()
                .departureTime(new Date())
                .arrivalTime(new Date())
                .occupiedSeats(0)
                .train(train)
                .build();

        // Save schedule
        schedule = scheduleRepository.save(schedule);

        // Create ticket linked to user, schedule, and roles
        TicketEntity ticket = TicketEntity.builder()
                .seatNumber(4)
                .user(user)
                .schedule(schedule)
                .build();

        // Save ticket
        TicketEntity savedTicket = ticketRepository.save(ticket);

        // Verify that the saved ticket is not null and has an ID
        Assertions.assertThat(savedTicket).isNotNull();
        Assertions.assertThat(savedTicket.getId()).isNotNull();
        Assertions.assertThat(savedTicket.getSeatNumber()).isEqualTo(ticket.getSeatNumber());
        Assertions.assertThat(savedTicket.getUser().getId()).isEqualTo(user.getId());
        Assertions.assertThat(savedTicket.getSchedule().getId()).isEqualTo(schedule.getId());
    }

    @Test
    public void TicketRepository_DeleteTicket_ReturnNoTicket() {
        // Create roles
        RoleEntity role = RoleEntity.builder().name("USER").build();
        RoleEntity adminRole = RoleEntity.builder().name("ADMIN").build();

        // Save roles
        roleRepository.save(role);
        roleRepository.save(adminRole);

        // Create user with roles
        UserEntity user = UserEntity.builder()
                .name("Chris")
                .surname("Williams")
                .email("chris.williams@example.com")
                .password("password")
                .roles(List.of(role, adminRole))
                .build();

        // Save user
        user = userRepository.save(user);

        // Create stations
        StationEntity departureStation = StationEntity.builder().name("Station 11").city("City 11").build();
        StationEntity arrivalStation = StationEntity.builder().name("Station 12").city("City 12").build();

        // Save stations
        departureStation = stationRepository.save(departureStation);
        arrivalStation = stationRepository.save(arrivalStation);

        // Create train
        TrainEntity train = TrainEntity.builder()
                .seats(50)
                .duration(Duration.ofHours(7))
                .trainNumber("113")
                .departureStation(departureStation)
                .arrivalStation(arrivalStation)
                .build();

        // Save train
        train = trainRepository.save(train);

        // Create schedule
        ScheduleEntity schedule = ScheduleEntity.builder()
                .departureTime(new Date())
                .arrivalTime(new Date())
                .occupiedSeats(0)
                .train(train)
                .build();

        // Save schedule
        schedule = scheduleRepository.save(schedule);

        // Create ticket linked to user, schedule, and roles
        TicketEntity ticket = TicketEntity.builder()
                .seatNumber(5)
                .user(user)
                .schedule(schedule)
                .build();

        // Save ticket
        ticket = ticketRepository.save(ticket);

        // Delete ticket
        ticketRepository.delete(ticket);

        // Verify that the ticket no longer exists
        boolean exists = ticketRepository.existsById(ticket.getId());
        Assertions.assertThat(exists).isFalse();
    }

}
