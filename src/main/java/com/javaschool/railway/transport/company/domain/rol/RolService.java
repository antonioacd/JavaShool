package com.javaschool.railway.transport.company.domain.rol;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RolService {

    private final RolRepository rolRepository;

    public RolEntity createRol(RolEntity rol){
        return rolRepository.save(rol);
    }

    public RolEntity getRolById(Long id) {
        return rolRepository.findById(id).get();
    }

    public List<RolInfoDTO> findAll(){

        List<RolEntity> rolEntityList = rolRepository.findAll();

        List<RolInfoDTO> rolInfoDTOList = new ArrayList<>();

        for (RolEntity rolEntity : rolEntityList) {
            // Add the DTO roles
            rolInfoDTOList.add(createInfoDTO(rolEntity));
        }

        return rolInfoDTOList;
    }

    private RolInfoDTO createInfoDTO(RolEntity rolEntity){

        RolInfoDTO rolInfoDto = new RolInfoDTO();

        rolInfoDto.setId(rolEntity.getId());
        rolInfoDto.setRol(rolEntity.getRol());

        return rolInfoDto;
    }

}
