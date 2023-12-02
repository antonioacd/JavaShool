package com.javaschool.railway.transport.company.domain.contollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaschool.railway.transport.company.domain.controllers.TrainController;
import com.javaschool.railway.transport.company.domain.entitites.StationEntity;
import com.javaschool.railway.transport.company.domain.entitites.TrainEntity;
import com.javaschool.railway.transport.company.domain.infodto.StationInfoDTO;
import com.javaschool.railway.transport.company.domain.infodto.TrainInfoDTO;
import com.javaschool.railway.transport.company.domain.services.TrainService;
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
class TrainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainService trainService;

    @InjectMocks
    private TrainController trainController;

    @Autowired
    private ObjectMapper objectMapper;

    private TrainEntity trainEntity;
    private TrainInfoDTO trainInfoDTO;

    @BeforeEach
    void setUp() {
        trainEntity = TrainEntity.builder()
                .id(1L)
                .seats(100)
                .duration(Duration.ofHours(2))
                .trainNumber("T123")
                .departureStation(StationEntity.builder().id(1L).name("Departure").city("City1").build())
                .arrivalStation(StationEntity.builder().id(2L).name("Arrival").city("City2").build())
                .build();

        trainInfoDTO = TrainInfoDTO.builder()
                .id(1L)
                .seats(100)
                .duration(Duration.ofHours(2))
                .trainNumber("T123")
                .departureStation(StationInfoDTO.builder().id(1L).name("Departure").city("City1").build())
                .arrivalStation(StationInfoDTO.builder().id(2L).name("Arrival").city("City2").build())
                .build();
    }

    @Test
    void createTrain_ReturnCreated() throws Exception {
        // Arrange
        given(trainService.createTrain(trainEntity)).willReturn(trainInfoDTO);

        // Act and Assert
        mockMvc.perform(post("/api/trains")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trainInfoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.seats", is(100)))
                .andExpect(jsonPath("$.trainNumber", is("T123")))
                .andExpect(jsonPath("$.departureStation.name", is("Departure")))
                .andExpect(jsonPath("$.arrivalStation.name", is("Arrival")));
    }

    @Test
    void updateTrain_ReturnUpdated() throws Exception {
        // Arrange
        Long trainId = 1L;
        given(trainService.updateTrain(eq(trainId), any(TrainInfoDTO.class))).willReturn(trainInfoDTO);

        // Act and Assert
        mockMvc.perform(put("/api/trains/{id}", trainId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trainInfoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.seats", is(100)))
                .andExpect(jsonPath("$.trainNumber", is("T123")))
                .andExpect(jsonPath("$.departureStation.name", is("Departure")))
                .andExpect(jsonPath("$.arrivalStation.name", is("Arrival")));
    }

    @Test
    void getTrainById_ReturnTrain() throws Exception {
        // Arrange
        Long trainId = 1L;
        given(trainService.getTrainById(trainId)).willReturn(trainInfoDTO);

        // Act and Assert
        mockMvc.perform(get("/api/trains/{id}", trainId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.seats", is(100)))
                .andExpect(jsonPath("$.trainNumber", is("T123")))
                .andExpect(jsonPath("$.departureStation.name", is("Departure")))
                .andExpect(jsonPath("$.arrivalStation.name", is("Arrival")));
    }

    @Test
    void deleteTrainById_ReturnNoContent() throws Exception {
        // Arrange
        Long trainId = 1L;

        // Act and Assert
        mockMvc.perform(delete("/api/trains/{id}", trainId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllTrains_ReturnListOfTrains() throws Exception {
        // Arrange
        List<TrainInfoDTO> trainList = Arrays.asList(trainInfoDTO, trainInfoDTO);
        given(trainService.getAllTrains()).willReturn(trainList);

        // Act and Assert
        mockMvc.perform(get("/api/trains")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].seats", is(100)))
                .andExpect(jsonPath("$[1].seats", is(100)));
    }

    @Test
    void findTrainsByDepartureStationAndArrivalStation_ReturnListOfTrains() throws Exception {
        // Arrange
        String departureStation = "Departure";
        String arrivalStation = "Arrival";
        List<TrainEntity> trainList = Arrays.asList(trainEntity, trainEntity);
        given(trainService.findTrainsByDepartureAndArrivalStations(departureStation, arrivalStation)).willReturn(trainList);

        // Act and Assert
        ResultActions resultActions = mockMvc.perform(get("/api/trains/searchByDepartureStationAndArrivalStation")
                        .param("departureStation", departureStation)
                        .param("arrivalStation", arrivalStation)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
