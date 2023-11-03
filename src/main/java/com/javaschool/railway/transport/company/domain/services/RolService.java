package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.RolEntity;
import com.javaschool.railway.transport.company.domain.infodto.RolInfoDTO;
import com.javaschool.railway.transport.company.domain.repositories.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing roles-related operations.
 */
@Service
@AllArgsConstructor
public class RolService {

    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final ModelMapper modelMapper;

    /**
     * Creates a new role and returns the role's information.
     *
     * @param rol The role entity to be created.
     * @return A DTO (Data Transfer Object) containing the role's information.
     */
    public RolInfoDTO createRol(RolEntity rol) {
        return modelMapper.map(roleRepository.save(rol), RolInfoDTO.class);
    }

    /**
     * Deletes a role by its ID.
     *
     * @param id The ID of the role to be deleted.
     */
    public void deleteRolById(Long id) {
        roleRepository.deleteById(id);
    }

    /**
     * Retrieves role information by its ID.
     *
     * @param id The ID of the role to retrieve.
     * @return A DTO containing the role's information.
     * @throws EntityNotFoundException If the role is not found.
     */
    public RolInfoDTO getRolById(Long id) {
        RolEntity rolEntity = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        return modelMapper.map(rolEntity, RolInfoDTO.class);
    }

    /**
     * Retrieves a list of all roles.
     *
     * @return A list of DTOs containing role information.
     */
    public List<RolInfoDTO> getAllRoles() {
        List<RolEntity> roles = roleRepository.findAll();

        return roles.stream()
                .map(rol -> modelMapper.map(rol, RolInfoDTO.class))
                .collect(Collectors.toList());
    }
}
