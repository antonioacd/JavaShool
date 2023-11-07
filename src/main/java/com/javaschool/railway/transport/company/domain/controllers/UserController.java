package com.javaschool.railway.transport.company.domain.controllers;

import com.javaschool.railway.transport.company.domain.entitites.UserEntity;
import com.javaschool.railway.transport.company.domain.infodto.UserInfoDTO;
import com.javaschool.railway.transport.company.domain.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping
  public UserInfoDTO createUser(@RequestBody UserEntity user) {
    return userService.createUser(user);
  }

  @GetMapping("/{id}")
  public UserInfoDTO getUserById(@PathVariable Long id) {
    return userService.getUserById(id);
  }

  @GetMapping("/{email}")
  public UserInfoDTO getUserByEmail(@PathVariable String email) {
    return userService.getUserByEmail(email);
  }

  @GetMapping
  public List<UserInfoDTO> getAllUsers() {
    return userService.getAllUsers();
  }
}
