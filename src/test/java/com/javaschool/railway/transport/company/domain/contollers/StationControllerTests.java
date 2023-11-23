package com.javaschool.railway.transport.company.domain.contollers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaschool.railway.transport.company.domain.controllers.StationController;
import com.javaschool.railway.transport.company.domain.entitites.StationEntity;
import com.javaschool.railway.transport.company.domain.infodto.StationInfoDTO;
import com.javaschool.railway.transport.company.domain.services.StationService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(classes = StationController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class StationControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StationService stationService;

    @Autowired
    private ObjectMapper objectMapper;
    private StationEntity station;
    private StationInfoDTO stationDTO;

    @BeforeEach
    public void init() {
        station = StationEntity.builder().name("station1").city("city1").build();
        stationDTO = StationInfoDTO.builder().name("station1").city("city1").build();
    }

    @Test
    public void StationController_CreateStation_ReturnCreated() throws Exception {
        given(stationService.createStation(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/api/stations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stationDTO)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(stationDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city", CoreMatchers.is(stationDTO.getCity())));
    }

    /*@Test
    public void PokemonController_GetAllPokemon_ReturnResponseDto() throws Exception {
        PokemonResponse responseDto = PokemonResponse.builder().pageSize(10).last(true).pageNo(1).content(Arrays.asList(stationDTO)).build();
        when(stationService.getAllPokemon(1,10)).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get("/api/pokemon")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo","1")
                .param("pageSize", "10"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDto.getContent().size())));
    }*/

    /*@Test
    public void PokemonController_PokemonDetail_ReturnPokemonDto() throws Exception {
        int pokemonId = 1;
        when(stationService.getPokemonById(pokemonId)).thenReturn(stationDTO);

        ResultActions response = mockMvc.perform(get("/api/pokemon/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stationDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(stationDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(stationDTO.getType())));
    }*/

    /*@Test
    public void PokemonController_UpdatePokemon_ReturnPokemonDto() throws Exception {
        int pokemonId = 1;
        when(stationService.updatePokemon(stationDTO, pokemonId)).thenReturn(stationDTO);

        ResultActions response = mockMvc.perform(put("/api/pokemon/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stationDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(stationDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(stationDTO.getType())));
    }*/

    /*@Test
    public void PokemonController_DeletePokemon_ReturnString() throws Exception {
        int pokemonId = 1;
        doNothing().when(stationService).deletePokemonId(1);

        ResultActions response = mockMvc.perform(delete("/api/pokemon/1/delete")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }*/
}