package com.javaschool.railway.transport.company.domain.respository;

import com.javaschool.railway.transport.company.domain.entitites.ScheduleEntity;
import com.javaschool.railway.transport.company.domain.entitites.StationEntity;
import com.javaschool.railway.transport.company.domain.entitites.TrainEntity;
import com.javaschool.railway.transport.company.domain.repositories.ScheduleRepository;
import com.javaschool.railway.transport.company.domain.repositories.StationRepository;
import com.javaschool.railway.transport.company.domain.repositories.TrainRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Duration;
import java.util.Date;
import java.util.List;

/**
 * Test class for the ScheduleRepository.
 */
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ScheduleRepositoryTest {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private StationRepository stationRepository;

    /**
     * Test case to verify saving a list of schedules.
     */
    @Test
    public void ScheduleRepository_SaveAll_ReturnSavedSchedules() {
        // Create two stations
        StationEntity station1 = StationEntity.builder()
                .name("Station 1")
                .city("City 1")
                .build();
        StationEntity station2 = StationEntity.builder()
                .name("Station 2")
                .city("City 2")
                .build();
        stationRepository.save(station1);
        stationRepository.save(station2);

        // Create a train
        TrainEntity train = TrainEntity.builder()
                .seats(100)
                .duration(Duration.ofHours(2))
                .trainNumber("T123")
                .departureStation(station1)
                .arrivalStation(station2)
                .build();
        trainRepository.save(train);

        // Create a list of schedules
        List<ScheduleEntity> schedules = List.of(
                ScheduleEntity.builder()
                        .departureTime(new Date())
                        .arrivalTime(new Date())
                        .occupiedSeats(0)
                        .train(train)
                        .build(),
                ScheduleEntity.builder()
                        .departureTime(new Date())
                        .arrivalTime(new Date())
                        .occupiedSeats(0)
                        .train(train)
                        .build()
        );

        // Save the schedules
        List<ScheduleEntity> savedSchedules = scheduleRepository.saveAll(schedules);

        // Verify the schedules are saved
        Assertions.assertThat(savedSchedules).isNotNull();
        Assertions.assertThat(savedSchedules).hasSize(2);
        for (ScheduleEntity savedSchedule : savedSchedules) {
            Assertions.assertThat(savedSchedule.getId()).isGreaterThan(0);
        }
    }

    /**
     * Test case to verify retrieving more than one schedule.
     */
    @Test
    public void ScheduleRepository_GetAll_ReturnMoreThanOneSchedule() {
        // Create two stations
        StationEntity station1 = StationEntity.builder()
                .name("Station 1")
                .city("City 1")
                .build();
        StationEntity station2 = StationEntity.builder()
                .name("Station 2")
                .city("City 2")
                .build();
        stationRepository.save(station1);
        stationRepository.save(station2);

        // Create a train
        TrainEntity train = TrainEntity.builder()
                .seats(100)
                .duration(Duration.ofHours(2))
                .trainNumber("T123")
                .departureStation(station1)
                .arrivalStation(station2)
                .build();
        trainRepository.save(train);

        // Create a list of schedules
        List<ScheduleEntity> schedules = List.of(
                ScheduleEntity.builder()
                        .departureTime(new Date())
                        .arrivalTime(new Date())
                        .occupiedSeats(0)
                        .train(train)
                        .build(),
                ScheduleEntity.builder()
                        .departureTime(new Date())
                        .arrivalTime(new Date())
                        .occupiedSeats(0)
                        .train(train)
                        .build()
        );

        // Save the schedules
        scheduleRepository.saveAll(schedules);

        // Retrieve all schedules
        List<ScheduleEntity> scheduleList = scheduleRepository.findAll();

        // Verify there are more than one schedule
        Assertions.assertThat(scheduleList).isNotNull();
        Assertions.assertThat(scheduleList).hasSizeGreaterThan(1);
    }

    /**
     * Test case to verify retrieving a schedule by ID.
     */
    @Test
    public void ScheduleRepository_GetById_ReturnMatchingSchedule() {
        // Create two stations
        StationEntity station1 = StationEntity.builder()
                .name("Station 1")
                .city("City 1")
                .build();
        StationEntity station2 = StationEntity.builder()
                .name("Station 2")
                .city("City 2")
                .build();
        stationRepository.save(station1);
        stationRepository.save(station2);

        // Create a train
        TrainEntity train = TrainEntity.builder()
                .seats(100)
                .duration(Duration.ofHours(2))
                .trainNumber("T123")
                .departureStation(station1)
                .arrivalStation(station2)
                .build();
        trainRepository.save(train);

        // Create a schedule
        ScheduleEntity schedule = ScheduleEntity.builder()
                .departureTime(new Date())
                .arrivalTime(new Date())
                .occupiedSeats(0)
                .train(train)
                .build();

        // Save the schedule
        ScheduleEntity savedSchedule = scheduleRepository.save(schedule);

        // Retrieve the schedule by ID
        ScheduleEntity foundSchedule = scheduleRepository.findById(savedSchedule.getId()).orElse(null);

        // Verify the retrieved schedule matches the saved schedule
        Assertions.assertThat(foundSchedule).isNotNull();
        Assertions.assertThat(foundSchedule.getId()).isEqualTo(savedSchedule.getId());
        Assertions.assertThat(foundSchedule.getDepartureTime()).isEqualTo(savedSchedule.getDepartureTime());
        Assertions.assertThat(foundSchedule.getArrivalTime()).isEqualTo(savedSchedule.getArrivalTime());
    }

    /**
     * Test case to verify retrieving schedules by cities and date.
     */
    @Test
    public void ScheduleRepository_FindSchedulesByCitiesAndDate_ReturnMatchingSchedules() {
        // Create three stations
        StationEntity station1 = StationEntity.builder()
                .name("Station 1")
                .city("City 1")
                .build();
        StationEntity station2 = StationEntity.builder()
                .name("Station 2")
                .city("City 2")
                .build();
        stationRepository.save(station1);
        stationRepository.save(station2);

        // Create a train
        TrainEntity train = TrainEntity.builder()
                .seats(100)
                .duration(Duration.ofHours(2))
                .trainNumber("T123")
                .departureStation(station1)
                .arrivalStation(station2)
                .build();
        trainRepository.save(train);

        // Create a schedule
        ScheduleEntity schedule = ScheduleEntity.builder()
                .departureTime(new Date())
                .arrivalTime(new Date())
                .occupiedSeats(0)
                .train(train)
                .build();

        // Save the schedule
        scheduleRepository.save(schedule);

        // Retrieve schedules by cities and date
        List<ScheduleEntity> foundSchedules = scheduleRepository.findSchedulesByCitiesAndDate("City 1", "City 2", "2023-11-19");

        // Verify the retrieved schedules match the saved schedule
        Assertions.assertThat(foundSchedules).isNotNull();
        Assertions.assertThat(foundSchedules.size()).isEqualTo(1);
        Assertions.assertThat(foundSchedules.get(0).getId()).isEqualTo(schedule.getId());
    }

    /**
     * Test case to verify retrieving schedules by train number.
     */
    @Test
    public void ScheduleRepository_FindSchedulesByTrainNumber_ReturnMatchingSchedules() {
        // Create two stations
        StationEntity station1 = StationEntity.builder()
                .name("Station 1")
                .city("City 1")
                .build();
        StationEntity station2 = StationEntity.builder()
                .name("Station 2")
                .city("City 2")
                .build();
        stationRepository.save(station1);
        stationRepository.save(station2);

        // Create a train
        TrainEntity train = TrainEntity.builder()
                .seats(100)
                .duration(Duration.ofHours(2))
                .trainNumber("T123")
                .departureStation(station1)
                .arrivalStation(station2)
                .build();
        trainRepository.save(train);

        // Create a schedule
        ScheduleEntity schedule = ScheduleEntity.builder()
                .departureTime(new Date())
                .arrivalTime(new Date())
                .occupiedSeats(0)
                .train(train)
                .build();

        // Save the schedule
        scheduleRepository.save(schedule);

        // Retrieve schedules by train number
        List<ScheduleEntity> foundSchedules = scheduleRepository.findSchedulesByTrainNumber("T123");

        // Verify the retrieved schedules match the saved schedule
        Assertions.assertThat(foundSchedules).isNotNull();
        Assertions.assertThat(foundSchedules.size()).isEqualTo(1);
        Assertions.assertThat(foundSchedules.get(0).getId()).isEqualTo(schedule.getId());
    }
}
