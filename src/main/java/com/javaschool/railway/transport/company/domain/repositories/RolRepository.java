package com.javaschool.railway.transport.company.domain.repositories;

import com.javaschool.railway.transport.company.domain.entitites.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RolRepository extends JpaRepository<RolEntity, Long> {

}