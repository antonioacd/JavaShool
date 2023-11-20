package com.javaschool.railway.transport.company.domain.infodto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;
import java.time.Duration;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class TrainInfoDTO {
    private Long id;
    private int seats;
    private Duration duration;
    private String trainNumber;
    private StationInfoDTO departureStation;
    private StationInfoDTO arrivalStation;
}
