package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.RolEntity;
import com.javaschool.railway.transport.company.domain.infodto.RolInfoDTO;
import com.javaschool.railway.transport.company.domain.repositories.RolRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RolService {

    private final RolRepository rolRepository;
    private final ModelMapper modelMapper;

    public RolInfoDTO createRol(RolInfoDTO rol) {
        System.out.println("Rol: " + rol.toString());
        RolEntity rolEntity = modelMapper.map(rol, RolEntity.class);
        rolEntity = rolRepository.save(rolEntity);

        System.out.println("RolEntity: " + rolEntity);
        return modelMapper.map(rolEntity, RolInfoDTO.class);
    }

    public RolInfoDTO getRolById(Long id) {
        RolEntity rolEntity = rolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));

        return modelMapper.map(rolEntity, RolInfoDTO.class);
    }

    public List<RolInfoDTO> findAll() {
        List<RolEntity> rolEntityList = rolRepository.findAll();

        return rolEntityList.stream()
                .map(rol -> modelMapper.map(rol, RolInfoDTO.class))
                .collect(Collectors.toList());
    }
}
