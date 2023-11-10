package com.javaschool.railway.transport.company.domain.repositories;

import com.javaschool.railway.transport.company.domain.entitites.StationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface StationRepository extends JpaRepository<StationEntity, Long> {

    @Query("SELECT station FROM StationEntity station " +
            "WHERE station.city = :city")
    List<StationEntity> findStationsByCity(
            @Param("city") String city
    );
}