package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.StationEntity;
import com.javaschool.railway.transport.company.domain.infodto.StationInfoDTO;
import com.javaschool.railway.transport.company.domain.repositories.StationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the StationService class.
 */
@ExtendWith(MockitoExtension.class)
public class StationServiceTest {

    @Mock
    private StationRepository stationRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private StationService stationService;

    /**
     * Tests the createStation method of the StationService class.
     * It verifies that the method returns a non-null StationDTO.
     */
    @Test
    public void createStation_ReturnsStationDTO() {
        // Arrange
        StationEntity station = StationEntity.builder()
                .name("Station")
                .city("City")
                .build();

        StationInfoDTO stationDTO = StationInfoDTO.builder()
                .name("Station")
                .city("City")
                .build();

        // Mocking behavior of stationRepository.save
        when(stationRepository.save(any(StationEntity.class))).thenReturn(station);

        // Mocking behavior of modelMapper.map
        when(modelMapper.map(station, StationInfoDTO.class)).thenReturn(stationDTO);

        // Calling the method under test
        StationInfoDTO savedStation = stationService.createStation(station);

        // Verifying that the result is not null
        assertThat(savedStation).isNotNull();
        // Verifying that the repository save method and modelMapper map method were called
        verify(stationRepository, times(1)).save(station);
        verify(modelMapper, times(1)).map(station, StationInfoDTO.class);
        // Verifying that the result matches the expected DTO
        assertEquals(stationDTO, savedStation);
    }

    /**
     * Tests the updateStation method of the StationService class.
     * It verifies that the method returns an updated StationDTO.
     */
    @Test
    public void updateStation_ReturnsUpdatedStationDTO() {
        // Existing StationEntity for the test
        Long stationId = 1L;
        StationEntity existingStation = StationEntity.builder()
                .id(stationId)
                .name("OldStation")
                .city("OldCity")
                .build();

        // Updated StationInfoDTO
        StationInfoDTO updatedStationInfoDTO = StationInfoDTO.builder()
                .name("NewStation")
                .city("NewCity")
                .build();

        // Mocking behavior of stationRepository.findById
        when(stationRepository.findById(stationId)).thenReturn(Optional.of(existingStation));

        // Mocking behavior of stationRepository.save
        when(stationRepository.save(existingStation)).thenReturn(existingStation);

        // Mocking behavior of modelMapper.map
        when(modelMapper.map(existingStation, StationInfoDTO.class)).thenReturn(updatedStationInfoDTO);

        // Calling the method under test
        StationInfoDTO updatedStation = stationService.updateStation(stationId, updatedStationInfoDTO);

        // Verifying that the result is not null and contains the updated information
        assertThat(updatedStation).isNotNull();
        assertThat(updatedStation.getName()).isEqualTo(updatedStationInfoDTO.getName());
        assertThat(updatedStation.getCity()).isEqualTo(updatedStationInfoDTO.getCity());
    }

    /**
     * Tests the deleteStationById method of the StationService class.
     * It verifies that the method deletes the station without throwing exceptions.
     */
    @Test
    public void deleteStationById_NoExceptionsThrown() {
        // Existing StationEntity for the test
        Long stationId = 1L;
        StationEntity existingStation = StationEntity.builder()
                .id(stationId)
                .name("Station")
                .city("City")
                .build();

        // Calling the method under test
        stationService.deleteStationById(stationId);

        // Verifying that no exceptions were thrown during the method execution
        verify(stationRepository, times(1)).deleteById(stationId);
    }

    /**
     * Tests the getStationById method of the StationService class.
     * It verifies that the method returns a non-null StationDTO.
     */
    @Test
    public void getStationById_ReturnsStationDTO() {
        // Existing StationEntity for the test
        Long stationId = 1L;
        StationEntity existingStation = StationEntity.builder()
                .id(stationId)
                .name("Station")
                .city("City")
                .build();

        // Mocking behavior of stationRepository.findById
        when(stationRepository.findById(stationId)).thenReturn(Optional.of(existingStation));

        // Mocking behavior of modelMapper.map
        when(modelMapper.map(existingStation, StationInfoDTO.class)).thenReturn(new StationInfoDTO());

        // Calling the method under test
        StationInfoDTO retrievedStation = stationService.getStationById(stationId);

        // Verifying that the result is not null
        assertThat(retrievedStation).isNotNull();
    }

    /**
     * Tests the getAllStations method of the StationService class.
     * It verifies that the method returns a list of non-null StationDTOs.
     */
    @Test
    public void StationService_GetAllStations_ReturnsStationDTOs() {
        // Arrange
        StationEntity station1 = StationEntity.builder()
                .name("Granada Station")
                .city("Granada")
                .build();

        StationInfoDTO stationDTO1 = StationInfoDTO.builder()
                .name("Granada Station")
                .city("Granada")
                .build();

        StationEntity station2 = StationEntity.builder()
                .name("Málaga Station")
                .city("Málaga")
                .build();

        StationInfoDTO stationDTO2 = StationInfoDTO.builder()
                .name("Málaga Station")
                .city("Málaga")
                .build();

        List<StationEntity> stations = Arrays.asList(station1, station2);
        List<StationInfoDTO> expectedStationDTOs = Arrays.asList(stationDTO1, stationDTO2);

        // Mocking behavior of stationRepository.findAll
        when(stationRepository.findAll()).thenReturn(stations);

        // Mocking behavior of modelMapper.map
        when(modelMapper.map(station1, StationInfoDTO.class)).thenReturn(stationDTO1);
        when(modelMapper.map(station2, StationInfoDTO.class)).thenReturn(stationDTO2);


        // Calling the method under test
        List<StationInfoDTO> allStations = stationService.getAllStations();

        // Verifying that the result is not null and contains the expected number of stations
        assertThat(allStations).isNotNull();
        assertThat(allStations).hasSize(stations.size());

        // Additional assertions for each station
        for (int i = 0; i < allStations.size(); i++) {
            assertThat(allStations.get(i).getName()).isEqualTo(stations.get(i).getName());
            assertThat(allStations.get(i).getCity()).isEqualTo(stations.get(i).getCity());
        }
    }

    /**
     * Tests the findStationsByCity method of the StationService class.
     * It verifies that the method returns a list of non-null StationEntities.
     */
    @Test
    public void findStationsByCity_ReturnsListOfStationEntities() {
        // List of StationEntity for the test
        List<StationEntity> stationsByCity = Collections.singletonList(
                StationEntity.builder().id(1L).name("Station1").city("City1").build()
        );

        // Mocking behavior of stationRepository.findStationsByCity
        when(stationRepository.findStationsByCity(anyString())).thenReturn(stationsByCity);

        // Calling the method under test
        List<StationEntity> foundStations = stationService.findStationsByCity("City1");

        // Verifying that the result is not null and contains the expected number of stations
        assertThat(foundStations).isNotNull();
        assertThat(foundStations).hasSize(stationsByCity.size());

        // Additional assertions for each station
        for (int i = 0; i < foundStations.size(); i++) {
            assertThat(foundStations.get(i).getName()).isEqualTo(stationsByCity.get(i).getName());
            assertThat(foundStations.get(i).getCity()).isEqualTo(stationsByCity.get(i).getCity());
        }
    }
}
