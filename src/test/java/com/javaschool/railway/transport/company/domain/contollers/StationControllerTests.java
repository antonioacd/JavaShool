package com.javaschool.railway.transport.company.domain.contollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaschool.railway.transport.company.domain.controllers.StationController;
import com.javaschool.railway.transport.company.domain.entitites.StationEntity;
import com.javaschool.railway.transport.company.domain.infodto.StationInfoDTO;
import com.javaschool.railway.transport.company.domain.services.StationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class StationControllerTests {

    @Mock
    private StationService stationService;

    @InjectMocks
    private StationController stationController;

    private MockMvc mockMvc;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(stationController).build();
    }

    @Test
    public void createStation_ReturnCreated() throws Exception {
        // Arrange
        StationEntity station = StationEntity.builder().name("station1").city("city1").build();
        given(stationService.createStation(station)).willReturn(StationInfoDTO.builder().id(1L).name("station1").city("city1").build());

        String responseContent = mockMvc.perform(post("/api/stations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(station)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

// Use the response content for assertions
        StationInfoDTO createdStation = objectMapper.readValue(responseContent, StationInfoDTO.class);
        assertNotNull(createdStation.getId());
        assertEquals("station1", createdStation.getName());
        assertEquals("city1", createdStation.getCity());
    }

    @Test
    public void updateStation_ReturnUpdated() throws Exception {
        // Arrange
        Long stationId = 1L;
        StationInfoDTO updatedStation = StationInfoDTO.builder().id(stationId).name("updatedStation").city("updatedCity").build();
        given(stationService.updateStation(stationId, updatedStation)).willReturn(updatedStation);

        // Act and Assert
        mockMvc.perform(put("/api/stations/{id}", stationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedStation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(stationId)))
                .andExpect(jsonPath("$.name", is("updatedStation")))
                .andExpect(jsonPath("$.city", is("updatedCity")));
    }

    @Test
    public void getAllStations_ReturnListOfStations() throws Exception {
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

    // Add more tests for getStationById, deleteStationById, and searchStations as needed
}
