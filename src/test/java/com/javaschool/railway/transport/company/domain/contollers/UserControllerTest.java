package com.javaschool.railway.transport.company.domain.contollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaschool.railway.transport.company.domain.entitites.UserEntity;
import com.javaschool.railway.transport.company.domain.infodto.RoleInfoDTO;
import com.javaschool.railway.transport.company.domain.infodto.UserInfoDTO;
import com.javaschool.railway.transport.company.domain.services.UserService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserInfoDTO userInfoDTO;
    private UserEntity userEntity;

    /**
     * Set up common data for tests.
     */
    @BeforeEach
    void setUp() {
        userEntity = UserEntity.builder()
                .id(1L)
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .password("password")
                .build();

        userInfoDTO = UserInfoDTO.builder()
                .id(1L)
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .roles(Arrays.asList(RoleInfoDTO.builder().id(1L).name("ROLE_USER").build()))
                .build();
    }

    /**
     * Test case for retrieving user information by ID.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    void getUserById_ReturnUser() throws Exception {
        // Arrange
        Long userId = 1L;
        given(userService.getUserById(userId)).willReturn(userInfoDTO);

        // Act and Assert
        mockMvc.perform(get("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("John")))
                .andExpect(jsonPath("$.surname", is("Doe")))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")))
                .andExpect(jsonPath("$.roles", hasSize(1)))
                .andExpect(jsonPath("$.roles[0].name", is("ROLE_USER")));
    }

    /**
     * Test case for retrieving user information by email.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    void getUserByEmail_ReturnUser() throws Exception {
        // Arrange
        String userEmail = "john.doe@example.com";
        given(userService.getUserByEmail(userEmail)).willReturn(userInfoDTO);

        // Act and Assert
        mockMvc.perform(get("/api/users/email/{email}", userEmail)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("John")))
                .andExpect(jsonPath("$.surname", is("Doe")))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")))
                .andExpect(jsonPath("$.roles", hasSize(1)))
                .andExpect(jsonPath("$.roles[0].name", is("ROLE_USER")));
    }

    /**
     * Test case for retrieving a list of all users.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    void getAllUsers_ReturnListOfUsers() throws Exception {
        // Arrange
        List<UserInfoDTO> userList = Arrays.asList(userInfoDTO);

        given(userService.getAllUsers()).willReturn(userList);

        // Act and Assert
        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("John")))
                .andExpect(jsonPath("$[0].surname", is("Doe")))
                .andExpect(jsonPath("$[0].email", is("john.doe@example.com")))
                .andExpect(jsonPath("$[0].roles", hasSize(1)))
                .andExpect(jsonPath("$[0].roles[0].name", is("ROLE_USER")));
    }

    /**
     * Test case for retrieving a list of users by schedule ID.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    void getUsersByScheduleId_ReturnListOfUsers() throws Exception {
        // Arrange
        Long scheduleId = 1L;
        List<UserEntity> userList = Arrays.asList(userEntity);

        given(userService.getUsersByScheduleId(scheduleId)).willReturn(userList);

        // Act and Assert
        mockMvc.perform(get("/api/users/passengers/{scheduleId}", scheduleId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("John")))
                .andExpect(jsonPath("$[0].surname", is("Doe")))
                .andExpect(jsonPath("$[0].email", is("john.doe@example.com")));
    }
}
