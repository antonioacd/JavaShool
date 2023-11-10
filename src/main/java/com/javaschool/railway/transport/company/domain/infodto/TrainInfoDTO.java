package com.javaschool.railway.transport.company.domain.infodto;

import lombok.NoArgsConstructor;
import lombok.Data;
import java.time.Duration;

@NoArgsConstructor
@Data
public class TrainInfoDTO {
    private Long id;
    private String seats;
    private Duration duration;
    private String trainNumber;
    private StationInfoDTO departureStation;
    private StationInfoDTO arrivalStation;
}
