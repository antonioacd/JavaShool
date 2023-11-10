package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.UserEntity;
import com.javaschool.railway.transport.company.domain.infodto.RoleInfoDTO;
import com.javaschool.railway.transport.company.domain.infodto.UserInfoDTO;
import com.javaschool.railway.transport.company.domain.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing user-related operations.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Deletes a user by their user ID.
     *
     * @param id The ID of the user to be deleted.
     */
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Retrieves user information by their user ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return A DTO containing the user's information.
     * @throws EntityNotFoundException If the user is not found.
     */
    public UserInfoDTO getUserById(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return modelMapper.map(user, UserInfoDTO.class);
    }

    /**
     * Retrieves user information by their user ID.
     *
     * @param email The email of the user to retrieve.
     * @return A DTO containing the user's information.
     * @throws EntityNotFoundException If the user is not found.
     */
    public UserInfoDTO getUserByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        UserInfoDTO userInfoDTO = modelMapper.map(user, UserInfoDTO.class);
        userInfoDTO.setRoles(user.getRoles().stream()
                .map(rol -> modelMapper.map(rol, RoleInfoDTO.class))
                .collect(Collectors.toList()));

        System.out.println("user: " + userInfoDTO.toString());

        return userInfoDTO;
    }

    /**
     * Retrieves a list of all users.
     *
     * @return A list of DTOs containing user information.
     */
    public List<UserInfoDTO> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();

        return users.stream()
                .map(user -> modelMapper.map(user, UserInfoDTO.class))
                .collect(Collectors.toList());
    }
}
