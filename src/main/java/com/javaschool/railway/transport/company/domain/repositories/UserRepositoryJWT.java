package com.javaschool.railway.transport.company.domain.repositories;

import com.javaschool.railway.transport.company.domain.entitites.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryJWT {
    public UserEntity findUserByEmail(String email){
        UserEntity user = new UserEntity(email,"123456");
        user.setName("name");
        user.setSurname("surname");
        return user;
    }
}
