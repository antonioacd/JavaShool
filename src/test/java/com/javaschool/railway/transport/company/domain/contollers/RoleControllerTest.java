package com.javaschool.railway.transport.company.domain.contollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaschool.railway.transport.company.domain.entitites.RoleEntity;
import com.javaschool.railway.transport.company.domain.infodto.RoleInfoDTO;
import com.javaschool.railway.transport.company.domain.services.RoleService;
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

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @Autowired
    private ObjectMapper objectMapper;

    private RoleInfoDTO roleInfoDTO;
    private RoleEntity roleEntity;

    /**
     * Set up common data for tests.
     */
    @BeforeEach
    void setUp() {
        roleEntity = RoleEntity.builder().id(1L).name("ROLE_USER").build();
        roleInfoDTO = RoleInfoDTO.builder().id(1L).name("ROLE_USER").build();
    }

    /**
     * Test case for creating a new role.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    void createRole_ReturnCreated() throws Exception {
        // Arrange
        given(roleService.createRole(roleEntity)).willReturn(roleInfoDTO);

        // Act and Assert
        mockMvc.perform(post("/api/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roleEntity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("ROLE_USER")));
    }

    /**
     * Test case for retrieving role information by ID.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    void getRoleById_ReturnRole() throws Exception {
        // Arrange
        Long roleId = 1L;
        given(roleService.getRoleById(roleId)).willReturn(roleInfoDTO);

        // Act and Assert
        mockMvc.perform(get("/api/roles/{id}", roleId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("ROLE_USER")));
    }

    /**
     * Test case for retrieving a list of all roles.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    void getAllRoles_ReturnListOfRoles() throws Exception {
        // Arrange
        List<RoleInfoDTO> roleList = Arrays.asList(
                RoleInfoDTO.builder().id(1L).name("ROLE_USER").build(),
                RoleInfoDTO.builder().id(2L).name("ROLE_ADMIN").build()
        );

        given(roleService.getAllRoles()).willReturn(roleList);

        // Act and Assert
        mockMvc.perform(get("/api/roles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("ROLE_USER")))
                .andExpect(jsonPath("$[1].name", is("ROLE_ADMIN")));
    }
}
