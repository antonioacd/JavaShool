package com.javaschool.railway.transport.company.rol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolController {

  @Autowired
  private RolRepository rolRepository;

  @GetMapping
  public List<Rol> getAllRoles() {
    return rolRepository.findAll();
  } 

  @GetMapping("/{id}")
  public Rol getRolById(@PathVariable Long id) {
    return rolRepository.findById(id).get();
  }

  @PostMapping
  public Rol createRol(@RequestBody Rol rol) {
    return rolRepository.save(rol);
  }
}