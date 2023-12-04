package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.RoleEntity;
import com.javaschool.railway.transport.company.domain.entitites.UserEntity;
import com.javaschool.railway.transport.company.domain.infodto.RoleInfoDTO;
import com.javaschool.railway.transport.company.domain.infodto.UserInfoDTO;
import com.javaschool.railway.transport.company.domain.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void deleteUserById_NoExceptionsThrown() {
        // Arrange
        Long userId = 1L;

        // Act
        userService.deleteUserById(userId);

        // Assert
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void getUserById_ReturnsUserDTO() {
        // Arrange
        Long userId = 1L;
        UserEntity existingUser = UserEntity.builder()
                .id(userId)
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .build();

        UserInfoDTO userDTO = UserInfoDTO.builder()
                .id(userId)
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .build();

        // Mocking behavior of userRepository.findById
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        // Mocking behavior of modelMapper.map
        when(modelMapper.map(existingUser, UserInfoDTO.class)).thenReturn(userDTO);

        // Act
        UserInfoDTO retrievedUser = userService.getUserById(userId);

        // Assert
        assertThat(retrievedUser).isNotNull();
        verify(userRepository, times(1)).findById(userId);
        verify(modelMapper, times(1)).map(existingUser, UserInfoDTO.class);
        assertEquals(userDTO, retrievedUser);
    }

    @Test
    void getUserByEmail_ReturnsUserDTOWithRoles() {
        // Arrange
        String userEmail = "john.doe@example.com";
        UserEntity existingUser = UserEntity.builder()
                .id(1L)
                .name("John")
                .surname("Doe")
                .email(userEmail)
                .password("password")
                .roles(Collections.singletonList(RoleEntity.builder().id(1L).name("ROLE_USER").build()))
                .build();

        UserInfoDTO userDTO = UserInfoDTO.builder()
                .id(1L)
                .name("John")
                .surname("Doe")
                .email(userEmail)
                .roles(Collections.singletonList(RoleInfoDTO.builder().id(1L).name("ROLE_USER").build()))
                .build();

        // Mocking behavior of userRepository.findByEmail
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(existingUser));

        // Mocking behavior of modelMapper.map
        when(modelMapper.map(existingUser, UserInfoDTO.class)).thenReturn(userDTO);
        when(modelMapper.map(any(RoleEntity.class), eq(RoleInfoDTO.class)))
                .thenReturn(RoleInfoDTO.builder().id(1L).name("ROLE_USER").build());

        // Act
        UserInfoDTO retrievedUser = userService.getUserByEmail(userEmail);

        // Assert
        assertThat(retrievedUser).isNotNull();
        verify(userRepository, times(1)).findByEmail(userEmail);
        verify(modelMapper, times(1)).map(existingUser, UserInfoDTO.class);
        verify(modelMapper, times(1)).map(any(RoleEntity.class), eq(RoleInfoDTO.class));
        assertEquals(userDTO, retrievedUser);
    }

    @Test
    void getAllUsers_ReturnsUserDTOs() {
        // Arrange
        UserEntity user1 = UserEntity.builder()
                .id(1L)
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .build();

        UserInfoDTO userDTO1 = UserInfoDTO.builder()
                .id(1L)
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .build();

        UserEntity user2 = UserEntity.builder()
                .id(2L)
                .name("Jane")
                .surname("Doe")
                .email("jane.doe@example.com")
                .build();

        UserInfoDTO userDTO2 = UserInfoDTO.builder()
                .id(2L)
                .name("Jane")
                .surname("Doe")
                .email("jane.doe@example.com")
                .build();

        List<UserEntity> users = Arrays.asList(user1, user2);
        List<UserInfoDTO> expectedUserDTOs = Arrays.asList(userDTO1, userDTO2);

        // Mocking behavior of userRepository.findAll
        when(userRepository.findAll()).thenReturn(users);

        // Mocking behavior of modelMapper.map
        when(modelMapper.map(user1, UserInfoDTO.class)).thenReturn(userDTO1);
        when(modelMapper.map(user2, UserInfoDTO.class)).thenReturn(userDTO2);

        // Act
        List<UserInfoDTO> allUsers = userService.getAllUsers();

        // Assert
        assertThat(allUsers).hasSize(users.size());

        for (int i = 0; i < allUsers.size(); i++) {
            assertThat(allUsers.get(i).getEmail()).isEqualTo(users.get(i).getEmail());
        }
    }

    @Test
    void getUsersByScheduleId_ReturnsListOfUserEntities() {
        // Arrange
        Long scheduleId = 1L;
        List<UserEntity> usersByScheduleId = Collections.singletonList(
                UserEntity.builder().id(1L).name("John").surname("Doe").email("john.doe@example.com").build()
        );

        // Mocking behavior of userRepository.findUsersByScheduleId
        when(userRepository.findUsersByScheduleId(scheduleId)).thenReturn(usersByScheduleId);

        // Act
        List<UserEntity> foundUsers = userService.getUsersByScheduleId(scheduleId);

        // Assert
        assertThat(foundUsers).hasSize(usersByScheduleId.size());

        for (int i = 0; i < foundUsers.size(); i++) {
            assertThat(foundUsers.get(i).getEmail()).isEqualTo(usersByScheduleId.get(i).getEmail());
        }
    }
}
