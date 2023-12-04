package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.StationEntity;
import com.javaschool.railway.transport.company.domain.entitites.TrainEntity;
import com.javaschool.railway.transport.company.domain.infodto.StationInfoDTO;
import com.javaschool.railway.transport.company.domain.infodto.TrainInfoDTO;
import com.javaschool.railway.transport.company.domain.repositories.StationRepository;
import com.javaschool.railway.transport.company.domain.repositories.TrainRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.Duration;
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
 * Unit tests for the TrainService class.
 */
@ExtendWith(MockitoExtension.class)
class TrainServiceTest {

    @Mock
    private TrainRepository trainRepository;

    @Mock
    private StationRepository stationRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TrainService trainService;

    /**
     * Test for creating a train and returning a TrainInfoDTO.
     */
    @Test
    void createTrain_ReturnsTrainDTO() {
        // Arrange
        TrainEntity train = TrainEntity.builder()
                .seats(100)
                .duration(Duration.ofHours(2))
                .trainNumber("T123")
                .departureStation(createStation("DepartureStation", "City1"))
                .arrivalStation(createStation("ArrivalStation", "City2"))
                .build();

        TrainInfoDTO trainDTO = TrainInfoDTO.builder()
                .seats(100)
                .duration(Duration.ofHours(2))
                .trainNumber("T123")
                .departureStation(createStationDTO("DepartureStation", "City1"))
                .arrivalStation(createStationDTO("ArrivalStation", "City2"))
                .build();

        // Mocking behavior of trainRepository.save
        when(trainRepository.save(any(TrainEntity.class))).thenReturn(train);

        // Mocking behavior of modelMapper.map
        when(modelMapper.map(train, TrainInfoDTO.class)).thenReturn(trainDTO);

        // Calling the method under test
        TrainInfoDTO savedTrain = trainService.createTrain(train);

        // Verifying that the result is not null
        assertThat(savedTrain).isNotNull();
        // Verifying that the repository save method and modelMapper map method were called
        verify(trainRepository, times(1)).save(train);
        verify(modelMapper, times(1)).map(train, TrainInfoDTO.class);
        // Verifying that the result matches the expected DTO
        assertEquals(trainDTO, savedTrain);
    }

    /**
     * Test for updating a train and returning an updated TrainInfoDTO.
     */
    @Test
    void updateTrain_ReturnsUpdatedTrainDTO() {
        // Arrange
        Long trainId = 1L;
        TrainEntity existingTrain = TrainEntity.builder()
                .id(trainId)
                .seats(100)
                .duration(Duration.ofHours(2))
                .trainNumber("T123")
                .departureStation(createStation("DepartureStation", "City1"))
                .arrivalStation(createStation("ArrivalStation", "City2"))
                .build();

        TrainInfoDTO updatedTrainDTO = TrainInfoDTO.builder()
                .id(trainId)
                .seats(150)
                .duration(Duration.ofHours(3))
                .trainNumber("T456")
                .departureStation(createStationDTO("UpdatedDepartureStation", "UpdatedCity1"))
                .arrivalStation(createStationDTO("UpdatedArrivalStation", "UpdatedCity2"))
                .build();

        // Mocking behavior of trainRepository.findById
        when(trainRepository.findById(trainId)).thenReturn(Optional.of(existingTrain));

        // Mocking behavior of trainRepository.save
        when(trainRepository.save(existingTrain)).thenReturn(existingTrain);

        // Mocking behavior of modelMapper.map
        when(modelMapper.map(existingTrain, TrainInfoDTO.class)).thenReturn(updatedTrainDTO);

        // Calling the method under test
        TrainInfoDTO updatedTrainResult = trainService.updateTrain(trainId, updatedTrainDTO);

        // Verifying that the result is not null and contains the updated information
        assertThat(updatedTrainResult).isNotNull();
        assertThat(updatedTrainResult.getSeats()).isEqualTo(updatedTrainDTO.getSeats());
        assertThat(updatedTrainResult.getDuration()).isEqualTo(updatedTrainDTO.getDuration());
        assertThat(updatedTrainResult.getTrainNumber()).isEqualTo(updatedTrainDTO.getTrainNumber());
    }

    /**
     * Test for deleting a train by its ID without any exceptions thrown.
     */
    @Test
    void deleteTrainById_NoExceptionsThrown() {
        // Arrange
        Long trainId = 1L;
        // Calling the method under test
        trainService.deleteTrainById(trainId);
        // Verifying that no exceptions were thrown during the method execution
        verify(trainRepository, times(1)).deleteById(trainId);
    }

    /**
     * Test for retrieving a train by its ID and returning a TrainInfoDTO.
     */
    @Test
    void getTrainById_ReturnsTrainDTO() {
        // Arrange
        Long trainId = 1L;
        TrainEntity existingTrain = TrainEntity.builder()
                .id(trainId)
                .seats(100)
                .duration(Duration.ofHours(2))
                .trainNumber("T123")
                .departureStation(createStation("DepartureStation", "City1"))
                .arrivalStation(createStation("ArrivalStation", "City2"))
                .build();

        TrainInfoDTO retrievedTrainDTO = TrainInfoDTO.builder()
                .id(trainId)
                .seats(100)
                .duration(Duration.ofHours(2))
                .trainNumber("T123")
                .departureStation(createStationDTO("DepartureStation", "City1"))
                .arrivalStation(createStationDTO("ArrivalStation", "City2"))
                .build();

        // Mocking behavior of trainRepository.findById
        when(trainRepository.findById(trainId)).thenReturn(Optional.of(existingTrain));

        // Mocking behavior of modelMapper.map
        when(modelMapper.map(existingTrain, TrainInfoDTO.class)).thenReturn(retrievedTrainDTO);

        // Calling the method under test
        TrainInfoDTO retrievedTrain = trainService.getTrainById(trainId);

        // Verifying that the result is not null
        assertThat(retrievedTrain).isNotNull();
    }

    /**
     * Test for retrieving all trains and returning a list of TrainInfoDTOs.
     */
    @Test
    void getAllTrains_ReturnsTrainDTOs() {
        // Arrange
        TrainEntity train1 = TrainEntity.builder()
                .id(1L)
                .seats(100)
                .duration(Duration.ofHours(2))
                .trainNumber("T123")
                .departureStation(createStation("DepartureStation1", "City1"))
                .arrivalStation(createStation("ArrivalStation1", "City2"))
                .build();

        TrainInfoDTO trainDTO1 = TrainInfoDTO.builder()
                .id(1L)
                .seats(100)
                .duration(Duration.ofHours(2))
                .trainNumber("T123")
                .departureStation(createStationDTO("DepartureStation1", "City1"))
                .arrivalStation(createStationDTO("ArrivalStation1", "City2"))
                .build();

        TrainEntity train2 = TrainEntity.builder()
                .id(2L)
                .seats(150)
                .duration(Duration.ofHours(3))
                .trainNumber("T456")
                .departureStation(createStation("DepartureStation2", "City3"))
                .arrivalStation(createStation("ArrivalStation2", "City4"))
                .build();

        TrainInfoDTO trainDTO2 = TrainInfoDTO.builder()
                .id(2L)
                .seats(150)
                .duration(Duration.ofHours(3))
                .trainNumber("T456")
                .departureStation(createStationDTO("DepartureStation2", "City3"))
                .arrivalStation(createStationDTO("ArrivalStation2", "City4"))
                .build();

        List<TrainEntity> trains = Arrays.asList(train1, train2);
        List<TrainInfoDTO> expectedTrainDTOs = Arrays.asList(trainDTO1, trainDTO2);

        // Mocking behavior of trainRepository.findAll
        when(trainRepository.findAll()).thenReturn(trains);

        // Mocking behavior of modelMapper.map
        when(modelMapper.map(train1, TrainInfoDTO.class)).thenReturn(trainDTO1);
        when(modelMapper.map(train2, TrainInfoDTO.class)).thenReturn(trainDTO2);

        // Calling the method under test
        List<TrainInfoDTO> allTrains = trainService.getAllTrains();

        // Verifying that the result is not null and contains the expected number of trains
        assertThat(allTrains).hasSize(trains.size());

        // Additional assertions for each train
        for (int i = 0; i < allTrains.size(); i++) {
            assertThat(allTrains.get(i).getSeats()).isEqualTo(trains.get(i).getSeats());
            assertThat(allTrains.get(i).getDuration()).isEqualTo(trains.get(i).getDuration());
            assertThat(allTrains.get(i).getTrainNumber()).isEqualTo(trains.get(i).getTrainNumber());
        }
    }

    /**
     * Test for finding trains by departure and arrival stations and returning a list of TrainEntities.
     */
    @Test
    void findTrainsByDepartureAndArrivalStations_ReturnsListOfTrainEntities() {
        // Arrange
        List<TrainEntity> trainsByStations = Collections.singletonList(
                TrainEntity.builder()
                        .id(1L)
                        .seats(100)
                        .duration(Duration.ofHours(2))
                        .trainNumber("T123")
                        .departureStation(createStation("DepartureStation", "City1"))
                        .arrivalStation(createStation("ArrivalStation", "City2"))
                        .build()
        );

        // Mocking behavior of trainRepository.findTrainsByDepartureAndArrivalStations
        when(trainRepository.findTrainsByDepartureAndArrivalStations(anyString(), anyString())).thenReturn(trainsByStations);

        // Calling the method under test
        List<TrainEntity> foundTrains = trainService.findTrainsByDepartureAndArrivalStations("City1", "City2");

        // Verifying that the result is not null and contains the expected number of trains
        assertThat(foundTrains).hasSize(trainsByStations.size());

        // Additional assertions for each train
        for (int i = 0; i < foundTrains.size(); i++) {
            assertThat(foundTrains.get(i).getSeats()).isEqualTo(trainsByStations.get(i).getSeats());
            assertThat(foundTrains.get(i).getDuration()).isEqualTo(trainsByStations.get(i).getDuration());
            assertThat(foundTrains.get(i).getTrainNumber()).isEqualTo(trainsByStations.get(i).getTrainNumber());
        }
    }

    /**
     * Helper method for creating instances of StationEntity.
     */
    private StationEntity createStation(String name, String city) {
        return StationEntity.builder()
                .name(name)
                .city(city)
                .build();
    }

    /**
     * Helper method for creating instances of StationInfoDTO.
     */
    private StationInfoDTO createStationDTO(String name, String city) {
        return StationInfoDTO.builder()
                .name(name)
                .city(city)
                .build();
    }
}
