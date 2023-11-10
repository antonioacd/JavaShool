package com.javaschool.railway.transport.company.domain.controllers;

import com.javaschool.railway.transport.company.domain.entitites.RoleEntity;
import com.javaschool.railway.transport.company.domain.infodto.RoleInfoDTO;
import com.javaschool.railway.transport.company.domain.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/roles")
@AllArgsConstructor
public class RoleController {

  private final RoleService roleService;

  @PostMapping
  public RoleInfoDTO createRole(@RequestBody RoleEntity rol) {
    return roleService.createRol(rol);
  }

  @GetMapping("/{id}")
  public RoleInfoDTO getRoleById(@PathVariable Long id) {
    return roleService.getRolById(id);
  }

  @GetMapping
  public List<RoleInfoDTO> getAllRoles() {
    return roleService.getAllRoles();
  }
}
