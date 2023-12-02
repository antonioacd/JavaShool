package com.javaschool.railway.transport.company.domain.contollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaschool.railway.transport.company.domain.entitites.StationEntity;
import com.javaschool.railway.transport.company.domain.infodto.StationInfoDTO;
import com.javaschool.railway.transport.company.domain.services.StationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class contains test cases for the StationController class.
 */
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class StationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StationService stationService;

    @Autowired
    private ObjectMapper objectMapper;

    private StationInfoDTO stationInfoDTO;
    private StationEntity station;

    /**
     * Set up common data for tests.
     */
    @BeforeEach
    void setUp() {
        station = StationEntity.builder().id(1L).name("station1").city("city1").build();
        stationInfoDTO = StationInfoDTO.builder().id(1L).name("station1").city("city1").build();
    }

    /**
     * Test case for creating a new station.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    void createStation_ReturnCreated() throws Exception {
        given(stationService.createStation(station)).willReturn(stationInfoDTO);

        // Act and Assert
        mockMvc.perform(post("/api/stations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stationInfoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("station1")))
                .andExpect(jsonPath("$.city", is("city1")));
    }

    /**
     * Test case for updating an existing station.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    void updateStation_ReturnUpdated() throws Exception {
        // Arrange
        Long stationId = 1L;
        StationInfoDTO updatedStation = StationInfoDTO.builder().id(stationId).name("updatedStation").city("updatedCity").build();
        given(stationService.updateStation(stationId, updatedStation)).willReturn(updatedStation);

        // Act and Assert
        mockMvc.perform(put("/api/stations/{id}", stationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedStation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("updatedStation")))
                .andExpect(jsonPath("$.city", is("updatedCity")));
    }

    /**
     * Test case for retrieving a list of all stations.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    void getAllStations_ReturnListOfStations() throws Exception {
        // Arrange
        List<StationInfoDTO> stationList = Arrays.asList(
                StationInfoDTO.builder().id(1L).name("station1").city("city1").build(),
                StationInfoDTO.builder().id(2L).name("station2").city("city2").build()
        );

        given(stationService.getAllStations()).willReturn(stationList);

        // Act and Assert
        mockMvc.perform(get("/api/stations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("station1")))
                .andExpect(jsonPath("$[1].name", is("station2")));
    }

    /**
     * Test case for retrieving station information by ID.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    void getStationById_ReturnStation() throws Exception {
        // Arrange
        Long stationId = 1L;
        given(stationService.getStationById(stationId)).willReturn(stationInfoDTO);

        // Act and Assert
        mockMvc.perform(get("/api/stations/{id}", stationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("station1")))
                .andExpect(jsonPath("$.city", is("city1")));
    }

    /**
     * Test case for deleting a station by ID.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    void deleteStationById_ReturnNoContent() throws Exception {
        // Arrange
        Long stationId = 1L;

        // Act and Assert
        mockMvc.perform(delete("/api/stations/{id}", stationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test case for searching stations by city.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    void searchStationsByCity_ReturnListOfStations() throws Exception {
        // Arrange
        String city = "city1";
        List<StationEntity> stationList = Arrays.asList(
                StationEntity.builder().id(1L).name("station1").city(city).build(),
                StationEntity.builder().id(2L).name("station2").city(city).build()
        );

        given(stationService.findStationsByCity(city)).willReturn(stationList);

        // Act and Assert
        ResultActions resultActions = mockMvc.perform(get("/api/stations/search")
                        .param("city", city)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Here you can add further assertions based on your specific response structure.
        // For example, you can check the size of the returned list, values of specific fields, etc.
    }

}
