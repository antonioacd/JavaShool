package com.javaschool.railway.transport.company.domain.infodto;

import com.javaschool.railway.transport.company.domain.entitites.StationEntity;
import com.javaschool.railway.transport.company.domain.entitites.TrainEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Data
@ToString
public class ScheduleInfoDTO {
    private Long id;
    private Date departureTime;
    private Date arrivalTime;
    private String occupiedSeats;
    private StationInfoDTO departureStation;
    private StationInfoDTO arrivalStation;
    private TrainInfoDTO train;
}
