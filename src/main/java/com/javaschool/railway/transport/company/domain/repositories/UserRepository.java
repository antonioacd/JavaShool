package com.javaschool.railway.transport.company.domain.repositories;

import com.javaschool.railway.transport.company.domain.entitites.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}