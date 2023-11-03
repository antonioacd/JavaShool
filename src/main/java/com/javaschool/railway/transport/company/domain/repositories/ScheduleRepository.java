package com.javaschool.railway.transport.company.domain.repositories;

import com.javaschool.railway.transport.company.domain.entitites.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

    @Query("SELECT s FROM ScheduleEntity s " +
            "WHERE s.train.departureStation.city = :departureCity " +
            "AND s.train.arrivalStation.city = :arrivalCity ")
    List<ScheduleEntity> findSchedulesByCitiesAndDate(
            @Param("departureCity") String departureCity,
            @Param("arrivalCity") String arrivalCity
    );

}

