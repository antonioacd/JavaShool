package com.javaschool.railway.transport.company.domain.controllers;

import com.javaschool.railway.transport.company.domain.infodto.AuthResponseDTO;
import com.javaschool.railway.transport.company.domain.infodto.LoginDTO;
import com.javaschool.railway.transport.company.domain.infodto.RegisterDTO;
import com.javaschool.railway.transport.company.domain.services.AuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private AuthService authService;

    /**
     * Handles user login.
     *
     * @param loginDTO The login data transfer object.
     * @return ResponseEntity containing the authentication response data transfer object.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }

    /**
     * Handles user registration.
     *
     * @param registerDTO The registration data transfer object.
     * @return ResponseEntity containing a message indicating the success or failure of the registration.
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        return authService.register(registerDTO);
    }
}
