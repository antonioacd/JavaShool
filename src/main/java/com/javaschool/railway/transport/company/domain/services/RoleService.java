package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.RoleEntity;
import com.javaschool.railway.transport.company.domain.infodto.RoleInfoDTO;
import com.javaschool.railway.transport.company.domain.repositories.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing roles-related operations.
 */
@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RoleService(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Creates a new role and returns the role's information.
     *
     * @param rol The role entity to be created.
     * @return A DTO (Data Transfer Object) containing the role's information.
     */
    public RoleInfoDTO createRol(RoleEntity rol) {
        return modelMapper.map(roleRepository.save(rol), RoleInfoDTO.class);
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
    public RoleInfoDTO getRolById(Long id) {
        RoleEntity roleEntity = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        return modelMapper.map(roleEntity, RoleInfoDTO.class);
    }

    /**
     * Retrieves a list of all roles.
     *
     * @return A list of DTOs containing role information.
     */
    public List<RoleInfoDTO> getAllRoles() {
        List<RoleEntity> roles = roleRepository.findAll();

        return roles.stream()
                .map(rol -> modelMapper.map(rol, RoleInfoDTO.class))
                .collect(Collectors.toList());
    }
}
