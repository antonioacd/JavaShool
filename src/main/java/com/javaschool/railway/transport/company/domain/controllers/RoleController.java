package com.javaschool.railway.transport.company.domain.controllers;

import com.javaschool.railway.transport.company.domain.entitites.RoleEntity;
import com.javaschool.railway.transport.company.domain.infodto.RoleInfoDTO;
import com.javaschool.railway.transport.company.domain.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/roles")
@AllArgsConstructor
public class RoleController {

    private final RoleService roleService;

    /**
     * Creates a new role.
     *
     * @param role The role entity to be created.
     * @return A DTO (Data Transfer Object) containing the role's information.
     */
    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public RoleInfoDTO createRole(@RequestBody RoleEntity role) {
        return roleService.createRole(role);
    }

    /**
     * Retrieves role information by its ID.
     *
     * @param id The ID of the role to retrieve.
     * @return A DTO containing the role's information.
     */
    @Secured({"ROLE_ADMIN"})
    @GetMapping("/{id}")
    public RoleInfoDTO getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    /**
     * Retrieves a list of all roles.
     *
     * @return A list of DTOs containing role information.
     */
    @Secured({"ROLE_ADMIN"})
    @GetMapping
    public List<RoleInfoDTO> getAllRoles() {
        return roleService.getAllRoles();
    }
}
