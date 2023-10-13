package com.javaschool.railway.transport.company.domain.controllers;

import com.javaschool.railway.transport.company.domain.entitites.RolEntity;
import com.javaschool.railway.transport.company.domain.infodto.RolInfoDTO;
import com.javaschool.railway.transport.company.domain.services.RolService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Same as controller but response bodies are by default
@RequestMapping("/api/roles")
@AllArgsConstructor
public class RolController {

  private final RolService rolService;

  @PostMapping
  public RolEntity createRol(@RequestBody RolEntity rol) {
    return rolService.createRol(rol);
  }

  @GetMapping("/{id}")
  public RolEntity getRolById(@PathVariable Long id) {
    return rolService.getRolById(id);
  }

  @GetMapping
  public List<RolInfoDTO> getAllRoles() {
    return rolService.findAll();
  }

}