package com.javaschool.railway.transport.company.domain.repositories;

import com.javaschool.railway.transport.company.domain.entitites.TrainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainRepository extends JpaRepository<TrainEntity, Long> {

}