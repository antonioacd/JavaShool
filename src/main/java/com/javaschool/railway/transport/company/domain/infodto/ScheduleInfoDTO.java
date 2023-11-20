package com.javaschool.railway.transport.company.domain.infodto;

import com.javaschool.railway.transport.company.domain.entitites.StationEntity;
import com.javaschool.railway.transport.company.domain.entitites.TrainEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class ScheduleInfoDTO {
    private Long id;
    private Date departureTime;
    private Date arrivalTime;
    private int occupiedSeats;
    private TrainInfoDTO train;
}
