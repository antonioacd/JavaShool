package com.javaschool.railway.transport.company.domain.infodto;

import com.javaschool.railway.transport.company.domain.entitites.StationEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Data
public class TrainInfoDTO {
    private Long id;
    private String seats;
    private StationInfoDTO currentStation;
}
