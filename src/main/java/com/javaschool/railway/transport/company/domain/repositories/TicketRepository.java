package com.javaschool.railway.transport.company.domain.repositories;

import com.javaschool.railway.transport.company.domain.entitites.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    @Query("SELECT ticket FROM TicketEntity ticket WHERE ticket.user.id = :userId " +
            "AND (:scheduleId IS NULL OR ticket.schedule.id = :scheduleId)")
    List<TicketEntity> findTicketsByUserIdAndScheduleId(
            @Param("userId") Long userId,
            @Param("scheduleId") Long scheduleId
    );

    @Query("SELECT t FROM TicketEntity t WHERE t.schedule.id = :scheduleId")
    List<TicketEntity> findTicketsByScheduleId(@Param("scheduleId") Long scheduleId);

    /**
     * Checks if a ticket with the given seat number exists in the specified schedule.
     *
     * @param scheduleId The ID of the schedule.
     * @param seatNumber The seat number to check.
     * @return true if a ticket exists, false if not.
     */
    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM TicketEntity t " +
            "WHERE t.schedule.id = :scheduleId AND t.seatNumber = :seatNumber")
    boolean existsByScheduleIdAndSeatNumber(
            @Param("scheduleId") Long scheduleId,
            @Param("seatNumber") int seatNumber
    );
}