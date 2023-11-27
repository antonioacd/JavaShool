package com.javaschool.railway.transport.company.domain.contollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaschool.railway.transport.company.domain.infodto.AuthResponseDTO;
import com.javaschool.railway.transport.company.domain.infodto.LoginDTO;
import com.javaschool.railway.transport.company.domain.infodto.RegisterDTO;
import com.javaschool.railway.transport.company.domain.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    private LoginDTO loginDTO;
    private AuthResponseDTO authResponseDTO;

    /**
     * Set up common data for tests.
     */
    @BeforeEach
    public void setUp() {
        loginDTO = LoginDTO.builder().email("user@example.com").password("password").build();
        authResponseDTO = AuthResponseDTO.builder().accessToken("mocked-token").build();
    }

    /**
     * Test case for user login.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    public void login_ReturnAuthToken() throws Exception {
        // Arrange

        given(authService.login(loginDTO)).willReturn(new ResponseEntity<>(new AuthResponseDTO("mocked-token"), HttpStatus.OK));

        // Act and Assert
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("mocked-token"));
    }

    /**
     * Test case for user registration.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    public void register_ReturnSuccessMessage() throws Exception {
        // Arrange
        String email = "newuser@example.com";
        String password = "newpassword";
        String name = "John";
        String surname = "Doe";
        RegisterDTO registerDTO = new RegisterDTO(email, password, name, surname);

        given(authService.register(registerDTO)).willReturn(ResponseEntity.ok("User registered success!"));

        // Act and Assert
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("User registered success!"));
    }
}
