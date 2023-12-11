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

    /**
     * Retrieves user information by their user ID.
     *
     * @param id The ID of the user to retrieve.
     * @return A DTO containing the user's information.
     */
    @GetMapping("/{id}")
    public UserInfoDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    /**
     * Retrieves user information by their email.
     *
     * @param email The email of the user to retrieve.
     * @return A DTO containing the user's information.
     */
    @GetMapping("email/{email}")
    public UserInfoDTO getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    /**
     * Retrieves a list of users associated with a specific schedule.
     *
     * @param scheduleId The ID of the schedule.
     * @return A list of user entities.
     */
    @GetMapping("/passengers/{scheduleId}")
    public List<UserEntity> getUsersByScheduleId(@PathVariable Long scheduleId) {
        return userService.getUsersByScheduleId(scheduleId);
    }

    /**
     * Retrieves a list of all users.
     *
     * @return A list of DTOs containing user information.
     */
    @GetMapping
    public List<UserInfoDTO> getAllUsers() {
        return userService.getAllUsers();
    }
}
