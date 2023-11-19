package com.javaschool.railway.transport.company.domain.respository;

import com.javaschool.railway.transport.company.domain.entitites.StationEntity;
import com.javaschool.railway.transport.company.domain.repositories.StationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

/**
 * Test class for StationRepository.
 */
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class StationRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    /**
     * Test saving a station and verifying that it is saved successfully.
     */
    @Test
    public void StationRepository_SaveAll_ReturnSavedStation() {
        // Create a station
        StationEntity station = StationEntity.builder()
                .name("Station")
                .city("City")
                .build();

        // Save the station
        StationEntity savedStation = stationRepository.save(station);

        // Verify the saved station is not null and has a positive ID
        Assertions.assertThat(savedStation).isNotNull();
        Assertions.assertThat(savedStation.getId()).isGreaterThan(0);
    }

    /**
     * Test getting all stations and verifying that the list contains more than one station.
     */
    @Test
    public void StationRepository_GetAll_ReturnMoreThanOneStation() {
        // Create two stations
        StationEntity station = StationEntity.builder()
                .name("Station")
                .city("City")
                .build();
        StationEntity station2 = StationEntity.builder()
                .name("Station 2")
                .city("City 2")
                .build();

        // Save the stations
        stationRepository.save(station);
        stationRepository.save(station2);

        // Retrieve all stations
        List<StationEntity> stationList = stationRepository.findAll();

        // Verify that the list is not null and contains two stations
        Assertions.assertThat(stationList).isNotNull();
        Assertions.assertThat(stationList.size()).isEqualTo(2);
    }

    /**
     * Test updating a station's name and verifying the name is updated successfully.
     */
    @Test
    public void StationRepository_UpdateStation_ReturnUpdatedStation() {
        // Create a station
        StationEntity station = StationEntity.builder()
                .name("Station")
                .city("City")
                .build();
        // Save the station
        StationEntity savedStation = stationRepository.save(station);

        // Update the station's name
        savedStation.setName("Updated Station");
        // Save the updated station
        StationEntity updatedStation = stationRepository.save(savedStation);

        // Verify that the updated station is not null and has the updated name
        Assertions.assertThat(updatedStation).isNotNull();
        Assertions.assertThat(updatedStation.getName()).isEqualTo("Updated Station");
    }

    /**
     * Test deleting a station and verifying that it no longer exists in the repository.
     */
    @Test
    public void StationRepository_DeleteStation_ReturnNoStation() {
        // Create a station
        StationEntity station = StationEntity.builder()
                .name("Station")
                .city("City")
                .build();
        // Save the station
        StationEntity savedStation = stationRepository.save(station);

        // Delete the station
        stationRepository.delete(savedStation);

        // Verify that the station no longer exists in the repository
        Assertions.assertThat(stationRepository.findById(savedStation.getId())).isEmpty();
    }

    /**
     * Test finding a station by its ID and verifying that it matches the saved station.
     */
    @Test
    public void StationRepository_FindById_ReturnMatchingStation() {
        // Create a station
        StationEntity station = StationEntity.builder()
                .name("Station")
                .city("City")
                .build();
        // Save the station
        StationEntity savedStation = stationRepository.save(station);

        // Find the station by ID
        StationEntity foundStation = stationRepository.findById(savedStation.getId()).orElse(null);

        // Verify that the found station is not null and has the correct ID
        Assertions.assertThat(foundStation).isNotNull();
        Assertions.assertThat(foundStation.getId()).isEqualTo(savedStation.getId());
    }

    /**
     * Test finding stations by city and verifying that the list contains the expected stations.
     */
    @Test
    public void StationRepository_FindStationsByCity_ReturnMatchingStations() {
        // Create two stations in the same city
        StationEntity station1 = StationEntity.builder()
                .name("Station 1")
                .city("City 1")
                .build();
        StationEntity station2 = StationEntity.builder()
                .name("Station 2")
                .city("City 1")
                .build();
        // Save the stations
        stationRepository.save(station1);
        stationRepository.save(station2);

        // Find stations by city
        List<StationEntity> foundStations = stationRepository.findStationsByCity("City 1");

        // Verify that the list is not null and contains two stations
        Assertions.assertThat(foundStations).isNotNull();
        Assertions.assertThat(foundStations.size()).isEqualTo(2);
    }
}
