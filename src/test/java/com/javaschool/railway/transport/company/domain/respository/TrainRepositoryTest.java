package com.javaschool.railway.transport.company.domain.respository;

import com.javaschool.railway.transport.company.domain.entitites.StationEntity;
import com.javaschool.railway.transport.company.domain.entitites.TrainEntity;
import com.javaschool.railway.transport.company.domain.repositories.StationRepository;
import com.javaschool.railway.transport.company.domain.repositories.TrainRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

/**
 * Test class for the TrainRepository.
 */
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class TrainRepositoryTest {

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private StationRepository stationRepository;

    /**
     * Test case to verify saving a train entity.
     */
    @Test
    void TrainRepository_SaveAll_ReturnSavedTrain() {
        // Create two stations
        StationEntity station1 = StationEntity.builder()
                .name("Station 1")
                .city("City 1")
                .build();
        StationEntity station2 = StationEntity.builder()
                .name("Station 2")
                .city("City 2")
                .build();

        // Save the stations
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

        // Save the train
        TrainEntity savedTrain = trainRepository.save(train);

        // Verify the train is saved
        Assertions.assertThat(savedTrain).isNotNull();
        Assertions.assertThat(savedTrain.getId()).isPositive();
    }

    /**
     * Test case to verify retrieving more than one train.
     */
    @Test
    void TrainRepository_GetAll_ReturnMoreThanOneTrain() {
        // Create two stations
        StationEntity station1 = StationEntity.builder()
                .name("Station 1")
                .city("City 1")
                .build();
        StationEntity station2 = StationEntity.builder()
                .name("Station 2")
                .city("City 2")
                .build();

        // Save the stations
        stationRepository.save(station1);
        stationRepository.save(station2);

        // Create two trains
        TrainEntity train1 = TrainEntity.builder()
                .seats(100)
                .duration(Duration.ofHours(2))
                .trainNumber("T123")
                .departureStation(station1)
                .arrivalStation(station2)
                .build();

        TrainEntity train2 = TrainEntity.builder()
                .seats(150)
                .duration(Duration.ofHours(3))
                .trainNumber("T456")
                .departureStation(station1)
                .arrivalStation(station2)
                .build();

        // Save the trains
        trainRepository.save(train1);
        trainRepository.save(train2);

        // Retrieve all trains
        Iterable<TrainEntity> trainList = trainRepository.findAll();

        // Verify there are more than one train
        Assertions.assertThat(trainList).hasSizeGreaterThan(1);
    }

    /**
     * Test case to verify saving and finding a train by ID.
     */
    @Test
    void TrainRepository_SaveAndFindById_ReturnMatchingTrain() {
        // Create a station
        StationEntity station1 = StationEntity.builder()
                .name("Station 1")
                .city("City 1")
                .build();
        stationRepository.save(station1);

        // Create a train
        TrainEntity train = TrainEntity.builder()
                .seats(100)
                .duration(Duration.ofHours(2))
                .trainNumber("T123")
                .departureStation(station1)
                .arrivalStation(station1)
                .build();

        // Save the train
        trainRepository.save(train);

        // Retrieve the train by ID
        Optional<TrainEntity> foundTrainOptional = trainRepository.findById(train.getId());
        TrainEntity foundTrain = foundTrainOptional.orElse(null);

        // Verify the retrieved train matches the saved train
        Assertions.assertThat(foundTrain).isNotNull();
        Assertions.assertThat(foundTrain.getId()).isEqualTo(train.getId());
        Assertions.assertThat(foundTrain.getTrainNumber()).isEqualTo(train.getTrainNumber());
    }

    /**
     * Test case to verify finding trains by departure and arrival stations.
     */
    @Test
    void TrainRepository_FindTrainsByDepartureAndArrivalStations_ReturnMatchingTrains() {
        // Create three stations
        StationEntity station1 = StationEntity.builder()
                .name("Station 1")
                .city("City 1")
                .build();
        StationEntity station2 = StationEntity.builder()
                .name("Station 2")
                .city("City 2")
                .build();
        StationEntity station3 = StationEntity.builder()
                .name("Station 3")
                .city("City 3")
                .build();

        // Save the stations
        stationRepository.save(station1);
        stationRepository.save(station2);
        stationRepository.save(station3);

        // Create two trains with different departure and arrival stations
        TrainEntity train1 = TrainEntity.builder()
                .seats(100)
                .duration(Duration.ofHours(2))
                .trainNumber("T123")
                .departureStation(station1)
                .arrivalStation(station2)
                .build();

        TrainEntity train2 = TrainEntity.builder()
                .seats(150)
                .duration(Duration.ofHours(3))
                .trainNumber("T456")
                .departureStation(station2)
                .arrivalStation(station3)
                .build();

        // Save the trains
        trainRepository.save(train1);
        trainRepository.save(train2);

        // Find trains with departure station "Station 1" and arrival station "Station 2"
        List<TrainEntity> foundTrains = trainRepository.findTrainsByDepartureAndArrivalStations("Station 1", "Station 2");

        // Verify the found trains match the expected results
        Assertions.assertThat(foundTrains)
                .isNotNull()
                .hasSize(1);
        Assertions.assertThat(foundTrains.get(0).getId()).isEqualTo(train1.getId());
    }
}
