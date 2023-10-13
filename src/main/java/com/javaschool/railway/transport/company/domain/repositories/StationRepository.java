package com.javaschool.railway.transport.company.domain.repositories;

import com.javaschool.railway.transport.company.domain.entitites.StationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<StationEntity, Long> {

}