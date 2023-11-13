package com.javaschool.railway.transport.company.domain.repositories;

import com.javaschool.railway.transport.company.domain.entitites.ScheduleEntity;
import com.javaschool.railway.transport.company.domain.entitites.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

    @Query("SELECT s FROM ScheduleEntity s " +
            "WHERE s.train.departureStation.city = :departureCity " +
            "AND s.train.arrivalStation.city = :arrivalCity " +
            "AND DATE_TRUNC('day', s.departureTime) = DATE_TRUNC('day', TO_TIMESTAMP(:selectedDate, 'YYYY-MM-DD'))")
    List<ScheduleEntity> findSchedulesByCitiesAndDate(
            @Param("departureCity") String departureCity,
            @Param("arrivalCity") String arrivalCity,
            @Param("selectedDate") String selectedDate
    );

    @Query("SELECT s FROM ScheduleEntity s " +
            "WHERE s.train.trainNumber = :trainNumber")
    List<ScheduleEntity> findSchedulesByTrainNumber(
            @Param("trainNumber") String trainNumber
    );
}