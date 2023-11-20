package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.RoleEntity;
import com.javaschool.railway.transport.company.domain.entitites.UserEntity;
import com.javaschool.railway.transport.company.domain.infodto.AuthResponseDTO;
import com.javaschool.railway.transport.company.domain.infodto.LoginDTO;
import com.javaschool.railway.transport.company.domain.infodto.RegisterDTO;
import com.javaschool.railway.transport.company.domain.repositories.RoleRepository;
import com.javaschool.railway.transport.company.domain.repositories.UserRepository;
import com.javaschool.railway.transport.company.domain.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Service class for handling authentication-related operations.
 */
@Service
public class AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtGenerator jwtGenerator;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager,
                       UserRepository userRepository, RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder, JwtGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    /**
     * Authenticates a user based on the provided login credentials.
     *
     * @param loginDTO The DTO containing user login information.
     * @return ResponseEntity containing the authentication token upon successful authentication.
     */
    public ResponseEntity<AuthResponseDTO> login(LoginDTO loginDTO) {
        try {
            // Attempt to authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),
                            loginDTO.getPassword()));

            // Set the authentication in the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate a JWT token
            String token = jwtGenerator.generateToken(authentication);

            // Return the token in the response
            return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
        } catch (Exception e) {
            // Handle authentication failure
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Registers a new user with the provided registration details.
     *
     * @param registerDTO The DTO containing user registration information.
     * @return ResponseEntity with a success message upon successful registration.
     */
    public ResponseEntity<String> register(RegisterDTO registerDTO) {
        // Check if the email is already taken
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        // Create a new user entity and populate it with registration details
        UserEntity user = new UserEntity();
        user.setName(registerDTO.getName());
        user.setSurname(registerDTO.getSurname());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        // Retrieve the "USER" role from the database or throw an exception if not found
        RoleEntity userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("Role 'USER' not found"));

        // Assign the role to the user
        user.setRoles(Collections.singletonList(userRole));

        // Save the user in the database
        userRepository.save(user);

        // Return a success message in the response
        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }

    public ResponseEntity<String> registerAdmin(RegisterDTO registerDTO) {
        // Check if the email is already taken
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        // Create a new user entity and populate it with registration details
        UserEntity user = new UserEntity();
        user.setName(registerDTO.getName());
        user.setSurname(registerDTO.getSurname());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        // Retrieve the "USER" role from the database or throw an exception if not found
        RoleEntity userRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new IllegalStateException("Role 'ADMIN' not found"));

        // Assign the role to the user
        user.setRoles(Collections.singletonList(userRole));

        // Save the user in the database
        userRepository.save(user);

        // Return a success message in the response
        return new ResponseEntity<>("Admin registered success!", HttpStatus.OK);
    }
}