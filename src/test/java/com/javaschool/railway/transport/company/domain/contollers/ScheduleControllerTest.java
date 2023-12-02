package com.javaschool.railway.transport.company.domain.contollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaschool.railway.transport.company.domain.controllers.ScheduleController;
import com.javaschool.railway.transport.company.domain.entitites.ScheduleEntity;
import com.javaschool.railway.transport.company.domain.entitites.TrainEntity;
import com.javaschool.railway.transport.company.domain.infodto.ScheduleInfoDTO;
import com.javaschool.railway.transport.company.domain.infodto.TrainInfoDTO;
import com.javaschool.railway.transport.company.domain.services.ScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScheduleService scheduleService;

    @InjectMocks
    private ScheduleController scheduleController;

    @Autowired
    private ObjectMapper objectMapper;

    private ScheduleEntity scheduleEntity;
    private ScheduleInfoDTO scheduleInfoDTO;

    @BeforeEach
    void setUp() {
        scheduleEntity = ScheduleEntity.builder()
                .id(1L)
                .departureTime(new Date())
                .arrivalTime(new Date())
                .occupiedSeats(50)
                .train(TrainEntity.builder().id(1L).seats(100).duration(Duration.ofHours(2)).trainNumber("T123").build())
                .build();

        scheduleInfoDTO = ScheduleInfoDTO.builder()
                .id(1L)
                .departureTime(new Date())
                .arrivalTime(new Date())
                .occupiedSeats(50)
                .train(TrainInfoDTO.builder().id(1L).seats(100).duration(Duration.ofHours(2)).trainNumber("T123").build())
                .build();
    }

    @Test
    void createSchedule_ReturnCreated() throws Exception {
        // Arrange
        given(scheduleService.createSchedule(scheduleEntity)).willReturn(scheduleInfoDTO);

        // Act and Assert
        mockMvc.perform(post("/api/schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(scheduleInfoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.occupiedSeats", is(50)))
                .andExpect(jsonPath("$.train.trainNumber", is("T123")));
    }

    @Test
    void updateSchedule_ReturnUpdated() throws Exception {
        // Arrange
        Long scheduleId = 1L;
        given(scheduleService.updateSchedule(eq(scheduleId), any(ScheduleInfoDTO.class))).willReturn(scheduleInfoDTO);

        // Act and Assert
        mockMvc.perform(put("/api/schedules/{id}", scheduleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(scheduleInfoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.occupiedSeats", is(50)))
                .andExpect(jsonPath("$.train.trainNumber", is("T123")));
    }

    @Test
    void getScheduleById_ReturnSchedule() throws Exception {
        // Arrange
        Long scheduleId = 1L;
        given(scheduleService.getScheduleById(scheduleId)).willReturn(scheduleInfoDTO);

        // Act and Assert
        mockMvc.perform(get("/api/schedules/{id}", scheduleId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.occupiedSeats", is(50)))
                .andExpect(jsonPath("$.train.trainNumber", is("T123")));
    }

    @Test
    void deleteScheduleById_ReturnNoContent() throws Exception {
        // Arrange
        Long scheduleId = 1L;

        // Act and Assert
        mockMvc.perform(delete("/api/schedules/{id}", scheduleId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllSchedules_ReturnListOfSchedules() throws Exception {
        // Arrange
        List<ScheduleInfoDTO> scheduleList = Arrays.asList(scheduleInfoDTO, scheduleInfoDTO);
        given(scheduleService.getAllSchedules()).willReturn(scheduleList);

        // Act and Assert
        mockMvc.perform(get("/api/schedules")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].occupiedSeats", is(50)))
                .andExpect(jsonPath("$[1].occupiedSeats", is(50)));
    }

    @Test
    void searchSchedulesByTrainNumber_ReturnListOfSchedules() throws Exception {
        // Arrange
        String trainNumber = "T123";
        List<ScheduleEntity> scheduleList = Arrays.asList(scheduleEntity, scheduleEntity);
        given(scheduleService.findByTrainNumber(trainNumber)).willReturn(scheduleList);

        // Act and Assert
        ResultActions resultActions = mockMvc.perform(get("/api/schedules/searchByTrainNumber")
                        .param("trainNumber", trainNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Here you can add further assertions based on your specific response structure.
        // For example, you can check the size of the returned list, values of specific fields, etc.
    }
}
