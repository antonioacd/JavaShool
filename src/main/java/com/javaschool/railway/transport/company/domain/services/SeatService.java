package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.SeatEntity;
import com.javaschool.railway.transport.company.domain.repositories.SeatRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    @Autowired
    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public void markSeatAsOccupied(int seatNumber, Long scheduleId) {
        Optional<SeatEntity> optionalSeat = seatRepository.findSeatByScheduleIdAndSeatNumber(scheduleId, seatNumber);
        SeatEntity seat = optionalSeat.orElseThrow(() -> new IllegalStateException("Seat not found"));
        seat.setOccupied(true);
        seatRepository.save(seat);
    }

    public void markSeatAsAvailable(int seatNumber, Long scheduleId) {
        Optional<SeatEntity> optionalSeat = seatRepository.findSeatByScheduleIdAndSeatNumber(scheduleId, seatNumber);
        SeatEntity seat = optionalSeat.orElseThrow(() -> new EntityNotFoundException("Seat not found with seat number: " + seatNumber));
        seat.setOccupied(false);
        seatRepository.save(seat);
    }

    public Optional<Integer> findNextAvailableSeat(Long scheduleId) {
        return seatRepository.findNextAvailableSeat(scheduleId);
    }
}

