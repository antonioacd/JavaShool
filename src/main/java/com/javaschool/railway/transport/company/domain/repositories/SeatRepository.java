package com.javaschool.railway.transport.company.domain.repositories;

import com.javaschool.railway.transport.company.domain.entitites.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<SeatEntity, Long> {

    @Query("SELECT MIN(se.seatNumber) FROM SeatEntity se " +
            "WHERE se.schedule.id = :scheduleId AND se.occupied = false")
    Optional<Integer> findNextAvailableSeat(@Param("scheduleId") Long scheduleId);

    @Query("SELECT se FROM SeatEntity se " +
            "WHERE se.schedule.id = :scheduleId AND se.seatNumber = :seatNumber")
    Optional<SeatEntity> findSeatByScheduleIdAndSeatNumber(@Param("scheduleId") Long scheduleId,
                                                           @Param("seatNumber") int seatNumber);

}
