package com.javaschool.railway.transport.company.domain.repositories;

import com.javaschool.railway.transport.company.domain.entitites.TrainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TrainRepository extends JpaRepository<TrainEntity, Long> {

    @Query("SELECT train FROM TrainEntity train " +
            "WHERE (:departureStation IS NULL OR train.departureStation.name = :departureStation) " +
            "AND (:arrivalStation IS NULL OR train.arrivalStation.name = :arrivalStation)")
    List<TrainEntity> findTrainsByDepartureAndArrivalStations(
            @Param("departureStation") String departureStation,
            @Param("arrivalStation") String arrivalStation
    );
}