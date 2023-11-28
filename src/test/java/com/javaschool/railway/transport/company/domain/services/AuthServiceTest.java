package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.RoleEntity;
import com.javaschool.railway.transport.company.domain.entitites.UserEntity;
import com.javaschool.railway.transport.company.domain.infodto.AuthResponseDTO;
import com.javaschool.railway.transport.company.domain.infodto.LoginDTO;
import com.javaschool.railway.transport.company.domain.infodto.RegisterDTO;
import com.javaschool.railway.transport.company.domain.repositories.RoleRepository;
import com.javaschool.railway.transport.company.domain.repositories.UserRepository;
import com.javaschool.railway.transport.company.domain.security.JwtGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtGenerator jwtGenerator;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void login_SuccessfulAuthentication_ReturnsToken() {
        // Arrange
        LoginDTO loginDTO = new LoginDTO("test@example.com", "password");
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtGenerator.generateToken(authentication)).thenReturn("generated-token");

        // Act
        ResponseEntity<AuthResponseDTO> responseEntity = authService.login(loginDTO);

        // Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getAccessToken()).isEqualTo("generated-token");
    }

    @Test
    public void login_AuthenticationFailure_ReturnsUnauthorized() {
        // Arrange
        LoginDTO loginDTO = new LoginDTO("test@example.com", "wrong-password");
        when(authenticationManager.authenticate(any())).thenThrow(new RuntimeException("Authentication failed"));

        // Act
        ResponseEntity<AuthResponseDTO> responseEntity = authService.login(loginDTO);

        // Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(responseEntity.getBody()).isNull();
    }

    @Test
    public void register_NewUser_ReturnsSuccessMessage() {
        // Arrange
        RegisterDTO registerDTO = new RegisterDTO("John", "Doe", "john@example.com", "password");
        when(userRepository.existsByEmail("john@example.com")).thenReturn(false);
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(RoleEntity.builder().id(1L).name("ROLE_USER").build()));
        when(passwordEncoder.encode("password")).thenReturn("encoded-password");

        // Act
        ResponseEntity<String> responseEntity = authService.register(registerDTO);

        // Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo("User registered success!");
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    /**
     * Tests the registration process when the user already exists.
     */
    @Test
    public void register_UserAlreadyExists_ReturnsBadRequest() {
        // Arrange
        RegisterDTO registerDTO = new RegisterDTO("john@example.com", "password", "Jhon", "Example");

        // Mock the scenario where the user already exists
        when(userRepository.existsByEmail("john@example.com")).thenReturn(true);

        // Mock other necessary dependencies
        when(roleRepository.findByName("USER")).thenReturn(Optional.of(RoleEntity.builder().id(1L).name("USER").build()));
        when(passwordEncoder.encode("password")).thenReturn("encoded-password");

        // Act
        ResponseEntity<String> responseEntity = authService.register(registerDTO);

        // Assert
        // Verify that the response is a BAD_REQUEST with the expected error message
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isEqualTo("Username is taken!");

        // Additional Explanation:
        // Since the user already exists, the registration process should not proceed to save the user.
        // Therefore, we verify that the save method is never called on the userRepository mock.
        // If the save method were called, it would indicate an issue with the registration logic.
        verify(userRepository, never()).save(any(UserEntity.class));
    }


}
