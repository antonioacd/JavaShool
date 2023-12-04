package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.ScheduleEntity;
import com.javaschool.railway.transport.company.domain.entitites.TrainEntity;
import com.javaschool.railway.transport.company.domain.infodto.ScheduleInfoDTO;
import com.javaschool.railway.transport.company.domain.infodto.TrainInfoDTO;
import com.javaschool.railway.transport.company.domain.repositories.ScheduleRepository;
import com.javaschool.railway.transport.company.domain.repositories.StationRepository;
import com.javaschool.railway.transport.company.domain.repositories.TrainRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the ScheduleService class.
 */
@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private StationRepository stationRepository;

    @Mock
    private TrainRepository trainRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ScheduleService scheduleService;

    /**
     * Test for creating a schedule and returning a ScheduleInfoDTO.
     */
    @Test
    void createSchedule_ReturnsScheduleDTO() {
        // Arrange
        ScheduleEntity schedule = ScheduleEntity.builder()
                .departureTime(new Date())
                .arrivalTime(new Date())
                .train(TrainEntity.builder().id(1L).build())
                .build();

        ScheduleInfoDTO scheduleDTO = ScheduleInfoDTO.builder()
                .departureTime(new Date())
                .arrivalTime(new Date())
                .train(TrainInfoDTO.builder().id(1L).build())
                .build();

        // Mocking behavior of trainRepository.getReferenceById
        when(trainRepository.getReferenceById(anyLong())).thenReturn(TrainEntity.builder().id(1L).build());

        // Mocking behavior of scheduleRepository.save
        when(scheduleRepository.save(any(ScheduleEntity.class))).thenReturn(schedule);

        // Mocking behavior of modelMapper.map
        when(modelMapper.map(schedule, ScheduleInfoDTO.class)).thenReturn(scheduleDTO);

        // Calling the method under test
        ScheduleInfoDTO savedSchedule = scheduleService.createSchedule(schedule);

        // Verifying that the result is not null
        assertThat(savedSchedule).isNotNull();
        // Verifying that the repository save method and modelMapper map method were called
        verify(scheduleRepository, times(1)).save(schedule);
        verify(modelMapper, times(1)).map(schedule, ScheduleInfoDTO.class);
        // Verifying that the result matches the expected DTO
        assertEquals(scheduleDTO, savedSchedule);
    }

    /**
     * Test for updating a schedule and returning an updated ScheduleInfoDTO.
     */
    @Test
    void updateSchedule_ReturnsUpdatedScheduleDTO() {
        // Arrange
        Long scheduleId = 1L;
        ScheduleEntity existingSchedule = ScheduleEntity.builder()
                .id(scheduleId)
                .departureTime(new Date())
                .arrivalTime(new Date())
                .train(TrainEntity.builder().id(1L).build())
                .build();

        ScheduleInfoDTO updatedScheduleDTO = ScheduleInfoDTO.builder()
                .id(scheduleId)
                .departureTime(new Date())
                .arrivalTime(new Date())
                .train(TrainInfoDTO.builder().id(2L).build())
                .build();

        // Mocking behavior of scheduleRepository.findById
        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(existingSchedule));

        // Mocking behavior of trainRepository.getReferenceById
        when(trainRepository.getReferenceById(anyLong())).thenReturn(TrainEntity.builder().id(2L).build());

        // Mocking behavior of scheduleRepository.save
        when(scheduleRepository.save(existingSchedule)).thenReturn(existingSchedule);

        // Mocking behavior of modelMapper.map
        when(modelMapper.map(existingSchedule, ScheduleInfoDTO.class)).thenReturn(updatedScheduleDTO);

        // Calling the method under test
        ScheduleInfoDTO updatedScheduleResult = scheduleService.updateSchedule(scheduleId, updatedScheduleDTO);

        // Verifying that the result is not null and contains the updated information
        assertThat(updatedScheduleResult).isNotNull();
        assertThat(updatedScheduleResult.getDepartureTime()).isEqualTo(updatedScheduleDTO.getDepartureTime());
        assertThat(updatedScheduleResult.getArrivalTime()).isEqualTo(updatedScheduleDTO.getArrivalTime());
        assertThat(updatedScheduleResult.getTrain().getId()).isEqualTo(updatedScheduleDTO.getTrain().getId());
    }

    /**
     * Test for deleting a schedule by its ID without any exceptions thrown.
     */
    @Test
    void deleteScheduleById_NoExceptionsThrown() {
        // Arrange
        Long scheduleId = 1L;
        // Calling the method under test
        scheduleService.deleteScheduleById(scheduleId);
        // Verifying that no exceptions were thrown during the method execution
        verify(scheduleRepository, times(1)).deleteById(scheduleId);
    }

    /**
     * Test for retrieving a schedule by its ID and returning a ScheduleInfoDTO.
     */
    @Test
    void getScheduleById_ReturnsScheduleDTO() {
        // Arrange
        Long scheduleId = 1L;
        ScheduleEntity existingSchedule = ScheduleEntity.builder()
                .id(scheduleId)
                .departureTime(new Date())
                .arrivalTime(new Date())
                .train(TrainEntity.builder().id(1L).build())
                .build();

        ScheduleInfoDTO retrievedScheduleDTO = ScheduleInfoDTO.builder()
                .id(scheduleId)
                .departureTime(new Date())
                .arrivalTime(new Date())
                .train(TrainInfoDTO.builder().id(1L).build())
                .build();

        // Mocking behavior of scheduleRepository.findById
        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(existingSchedule));

        // Mocking behavior of modelMapper.map
        when(modelMapper.map(existingSchedule, ScheduleInfoDTO.class)).thenReturn(retrievedScheduleDTO);

        // Calling the method under test
        ScheduleInfoDTO retrievedSchedule = scheduleService.getScheduleById(scheduleId);

        // Verifying that the result is not null
        assertThat(retrievedSchedule).isNotNull();
    }

    /**
     * Test for retrieving all schedules and returning a list of ScheduleInfoDTOs.
     */
    @Test
    void getAllSchedules_ReturnsScheduleDTOs() {

        Date defaultDate = new Date();
        // Arrange
        ScheduleEntity schedule1 = ScheduleEntity.builder()
                .id(1L)
                .departureTime(defaultDate)
                .arrivalTime(defaultDate)
                .train(TrainEntity.builder().id(1L).build())
                .build();

        ScheduleInfoDTO scheduleDTO1 = ScheduleInfoDTO.builder()
                .id(1L)
                .departureTime(defaultDate)
                .arrivalTime(defaultDate)
                .train(TrainInfoDTO.builder().id(1L).build())
                .build();

        ScheduleEntity schedule2 = ScheduleEntity.builder()
                .id(2L)
                .departureTime(defaultDate)
                .arrivalTime(defaultDate)
                .train(TrainEntity.builder().id(2L).build())
                .build();

        ScheduleInfoDTO scheduleDTO2 = ScheduleInfoDTO.builder()
                .id(2L)
                .departureTime(defaultDate)
                .arrivalTime(defaultDate)
                .train(TrainInfoDTO.builder().id(2L).build())
                .build();

        List<ScheduleEntity> schedules = Arrays.asList(schedule1, schedule2);
        List<ScheduleInfoDTO> expectedScheduleDTOs = Arrays.asList(scheduleDTO1, scheduleDTO2);

        // Mocking behavior of scheduleRepository.findAll
        when(scheduleRepository.findAll()).thenReturn(schedules);

        // Mocking behavior of modelMapper.map
        when(modelMapper.map(schedule1, ScheduleInfoDTO.class)).thenReturn(scheduleDTO1);
        when(modelMapper.map(schedule2, ScheduleInfoDTO.class)).thenReturn(scheduleDTO2);

        // Calling the method under test
        List<ScheduleInfoDTO> allSchedules = scheduleService.getAllSchedules();

        // Verifying that the result is not null and contains the expected number of schedules
        assertThat(allSchedules).hasSize(schedules.size());

        // Additional assertions for each schedule
        for (int i = 0; i < allSchedules.size(); i++) {
            assertThat(allSchedules.get(i).getDepartureTime()).isEqualTo(schedules.get(i).getDepartureTime());
            assertThat(allSchedules.get(i).getArrivalTime()).isEqualTo(schedules.get(i).getArrivalTime());
            assertThat(allSchedules.get(i).getTrain().getId()).isEqualTo(schedules.get(i).getTrain().getId());
        }
    }

    /**
     * Test for finding schedules by cities, arrival date, and returning a list of ScheduleEntities.
     */
    /*@Test
    void findSchedulesByCitiesAndDate_ReturnsListOfScheduleEntities() throws ParseException {
        // Arrange
        String departureCity = "City1";
        String arrivalCity = "City2";
        Date selectedDate = new SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01");

        List<ScheduleEntity> schedulesByCities = Collections.singletonList(
                ScheduleEntity.builder()
                        .id(1L)
                        .departureTime(selectedDate)
                        .arrivalTime(selectedDate)
                        .train(TrainEntity.builder().id(1L).build())
                        .build()
        );

        // Mocking behavior of scheduleRepository.findSchedulesByCitiesAndDate
        when(scheduleRepository.findSchedulesByCitiesAndDate(anyString(), anyString(), anyString())).thenReturn(schedulesByCities);

        // Calling the method under test
        List<ScheduleEntity> foundSchedules = scheduleService.findSchedulesByCitiesAndDate(departureCity, arrivalCity, selectedDate);

        // Verifying that the result is not null and contains the expected number of schedules
        assertThat(foundSchedules).isNotNull();
        assertThat(foundSchedules).hasSize(schedulesByCities.size());

        // Additional assertions for each schedule
        for (int i = 0; i < foundSchedules.size(); i++) {
            assertThat(foundSchedules.get(i).getDepartureTime()).isEqualTo(schedulesByCities.get(i).getDepartureTime());
            assertThat(foundSchedules.get(i).getArrivalTime()).isEqualTo(schedulesByCities.get(i).getArrivalTime());
            assertThat(foundSchedules.get(i).getTrain().getId()).isEqualTo(schedulesByCities.get(i).getTrain().getId());
        }
    }*/

    /**
     * Test for finding schedules by train number and returning a list of ScheduleEntities.
     */
    @Test
    void findSchedulesByTrainNumber_ReturnsListOfScheduleEntities() {
        // Arrange
        String trainNumber = "T123";

        List<ScheduleEntity> schedulesByTrainNumber = Arrays.asList(
                ScheduleEntity.builder()
                        .id(1L)
                        .departureTime(new Date())
                        .arrivalTime(new Date())
                        .train(TrainEntity.builder().id(1L).trainNumber(trainNumber).build())
                        .build(),
                ScheduleEntity.builder()
                        .id(2L)
                        .departureTime(new Date())
                        .arrivalTime(new Date())
                        .train(TrainEntity.builder().id(2L).trainNumber(trainNumber).build())
                        .build()
        );

        // Mocking behavior of scheduleRepository.findSchedulesByTrainNumber
        when(scheduleRepository.findSchedulesByTrainNumber(trainNumber)).thenReturn(schedulesByTrainNumber);

        // Calling the method under test
        List<ScheduleEntity> foundSchedules = scheduleService.findByTrainNumber(trainNumber);

        // Verifying that the result is not null and contains the expected number of schedules
        assertThat(foundSchedules).hasSize(schedulesByTrainNumber.size());

        // Additional assertions for each schedule
        for (int i = 0; i < foundSchedules.size(); i++) {
            assertThat(foundSchedules.get(i).getDepartureTime()).isEqualTo(schedulesByTrainNumber.get(i).getDepartureTime());
            assertThat(foundSchedules.get(i).getArrivalTime()).isEqualTo(schedulesByTrainNumber.get(i).getArrivalTime());
            assertThat(foundSchedules.get(i).getTrain().getId()).isEqualTo(schedulesByTrainNumber.get(i).getTrain().getId());
        }
    }

}
