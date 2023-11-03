package com.javaschool.railway.transport.company.domain.repositories;

import com.javaschool.railway.transport.company.domain.entitites.ScheduleEntity;
import com.javaschool.railway.transport.company.domain.entitites.TrainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainRepository extends JpaRepository<TrainEntity, Long> {
    @Query("SELECT train FROM TrainEntity train " +
            "WHERE train.departureStation.name = :departureStation " +
            "AND train.arrivalStation.name = :arrivalStation ")
    List<TrainEntity> findTrainsByDepartureStationAndArrivalStation(
            @Param("departureStation") String departureStation,
            @Param("arrivalStation") String arrivalStation
    );

    @Query("SELECT train FROM TrainEntity train " +
            "WHERE train.departureStation.name = :departureStation ")
    List<TrainEntity> findTrainsByDepartureStation(
            @Param("departureStation") String departureStation
    );

    @Query("SELECT train FROM TrainEntity train " +
            "WHERE train.arrivalStation.name = :arrivalStation ")
    List<TrainEntity> findTrainsByArrivalStation(
            @Param("arrivalStation") String arrivalStation
    );
}