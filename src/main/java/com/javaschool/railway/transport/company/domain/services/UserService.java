package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.UserEntity;
import com.javaschool.railway.transport.company.domain.infodto.UserInfoDTO;
import com.javaschool.railway.transport.company.domain.repositories.RolRepository;
import com.javaschool.railway.transport.company.domain.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing user-related operations.
 */
@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RolRepository rolRepository;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Creates a new user and returns the user's information.
     *
     * @param user The user entity to be created.
     * @return A DTO (Data Transfer Object) containing the user's information.
     */
    public UserInfoDTO createUser(UserEntity user) {
        user.setRol(rolRepository.getReferenceById(user.getRol().getId()));
        return modelMapper.map(userRepository.save(user), UserInfoDTO.class);
    }

    /**
     * Deletes a user by their user ID.
     *
     * @param id The ID of the user to be deleted.
     */
    public void deleteTrainById(Long id) {
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
