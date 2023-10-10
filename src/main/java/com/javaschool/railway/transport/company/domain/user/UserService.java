package com.javaschool.railway.transport.company.domain.user;

import com.javaschool.railway.transport.company.domain.rol.RolEntity;
import com.javaschool.railway.transport.company.domain.rol.RolRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity createUser(UserEntity user){
        return userRepository.save(user);
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public List<UserInfoDTO> findAll(){

        List<UserEntity> userEntityList = userRepository.findAll();

        List<UserInfoDTO> userInfoDTOList = new ArrayList<>();

        for (UserEntity userEntity : userEntityList) {
            // Add the DTO useres
            userInfoDTOList.add(createInfoDTO(userEntity));
        }

        return userInfoDTOList;
    }

    private UserInfoDTO createInfoDTO(UserEntity user){

        UserInfoDTO userInfoDto = new UserInfoDTO();

        userInfoDto.setId(user.getId());
        userInfoDto.setName(user.getName());
        userInfoDto.setSurname(user.getSurname());
        userInfoDto.setDate_of_birth(user.getDate_of_birth());

        return userInfoDto;
    }

}
