package com.javaschool.railway.transport.company.domain.infodto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
