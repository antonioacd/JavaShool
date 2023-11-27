package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.RoleEntity;
import com.javaschool.railway.transport.company.domain.infodto.RoleInfoDTO;
import com.javaschool.railway.transport.company.domain.repositories.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RoleService roleService;

    @Test
    public void createRole_ReturnsRoleDTO() {
        // Arrange
        RoleEntity role = RoleEntity.builder()
                .name("Admin")
                .build();

        RoleInfoDTO roleDTO = RoleInfoDTO.builder()
                .name("Admin")
                .build();

        // Mocking behavior of roleRepository.save
        when(roleRepository.save(any(RoleEntity.class))).thenReturn(role);

        // Mocking behavior of modelMapper.map
        when(modelMapper.map(role, RoleInfoDTO.class)).thenReturn(roleDTO);

        // Act
        RoleInfoDTO savedRole = roleService.createRole(role);

        // Assert
        assertThat(savedRole).isNotNull();
        verify(roleRepository, times(1)).save(role);
        verify(modelMapper, times(1)).map(role, RoleInfoDTO.class);
        assertEquals(roleDTO, savedRole);
    }

    @Test
    public void getRoleById_ReturnsRoleDTO() {
        // Arrange
        Long roleId = 1L;
        RoleEntity existingRole = RoleEntity.builder()
                .id(roleId)
                .name("User")
                .build();

        RoleInfoDTO roleDTO = RoleInfoDTO.builder()
                .id(roleId)
                .name("User")
                .build();

        // Mocking behavior of roleRepository.findById
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(existingRole));

        // Mocking behavior of modelMapper.map
        when(modelMapper.map(existingRole, RoleInfoDTO.class)).thenReturn(roleDTO);

        // Act
        RoleInfoDTO retrievedRole = roleService.getRoleById(roleId);

        // Assert
        assertThat(retrievedRole).isNotNull();
        verify(roleRepository, times(1)).findById(roleId);
        verify(modelMapper, times(1)).map(existingRole, RoleInfoDTO.class);
        assertEquals(roleDTO, retrievedRole);
    }

    @Test
    public void getAllRoles_ReturnsRoleDTOs() {
        // Arrange
        RoleEntity role1 = RoleEntity.builder()
                .id(1L)
                .name("Admin")
                .build();

        RoleInfoDTO roleDTO1 = RoleInfoDTO.builder()
                .id(1L)
                .name("Admin")
                .build();

        RoleEntity role2 = RoleEntity.builder()
                .id(2L)
                .name("User")
                .build();

        RoleInfoDTO roleDTO2 = RoleInfoDTO.builder()
                .id(2L)
                .name("User")
                .build();

        List<RoleEntity> roles = Arrays.asList(role1, role2);
        List<RoleInfoDTO> expectedRoleDTOs = Arrays.asList(roleDTO1, roleDTO2);

        // Mocking behavior of roleRepository.findAll
        when(roleRepository.findAll()).thenReturn(roles);

        // Mocking behavior of modelMapper.map
        when(modelMapper.map(role1, RoleInfoDTO.class)).thenReturn(roleDTO1);
        when(modelMapper.map(role2, RoleInfoDTO.class)).thenReturn(roleDTO2);

        // Act
        List<RoleInfoDTO> allRoles = roleService.getAllRoles();

        // Assert
        assertThat(allRoles).isNotNull();
        assertThat(allRoles).hasSize(roles.size());

        for (int i = 0; i < allRoles.size(); i++) {
            assertThat(allRoles.get(i).getName()).isEqualTo(roles.get(i).getName());
        }
    }
}
