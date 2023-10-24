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

@Service
@AllArgsConstructor
//@NoArgsConstructor(force = true)
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RolRepository rolRepository;
    @Autowired
    private ModelMapper modelMapper;

    public UserInfoDTO createUser(UserEntity user) {
        user.setRol(rolRepository.getReferenceById(user.getRol().getId()));
        return modelMapper.map(userRepository.save(user), UserInfoDTO.class);
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("Usuario no encontrado");
        }

        userRepository.deleteById(userId);
    }

    public UserInfoDTO getUserById(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        return modelMapper.map(user, UserInfoDTO.class);
    }

    public List<UserInfoDTO> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();

        return users.stream()
                .map(user -> modelMapper.map(user, UserInfoDTO.class))
                .collect(Collectors.toList());
    }
}
