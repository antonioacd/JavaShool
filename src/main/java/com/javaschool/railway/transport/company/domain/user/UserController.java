package com.javaschool.railway.transport.company.domain.user;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping
  public UserEntity createUser(@RequestBody UserEntity user) {
    return userService.createUser(user);
  }

  @GetMapping("/{id}")
  public UserEntity getUserById(@PathVariable Long id) {
    return userService.getUserById(id);
  }

  @GetMapping
  public List<UserInfoDTO> getAllUsers() {
    return userService.findAll();
  }

}