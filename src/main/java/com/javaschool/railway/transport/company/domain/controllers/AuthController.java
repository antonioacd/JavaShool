package com.javaschool.railway.transport.company.domain.controllers;

import com.javaschool.railway.transport.company.domain.infodto.AuthResponseDTO;
import com.javaschool.railway.transport.company.domain.infodto.LoginDTO;
import com.javaschool.railway.transport.company.domain.infodto.RegisterDTO;
import com.javaschool.railway.transport.company.domain.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
        return authService.register(registerDTO);
    }

}
