package com.javaschool.railway.transport.company.domain.infodto;

import com.javaschool.railway.transport.company.domain.entitites.StationEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Data
@ToString
public class TrainInfoDTO {
    private Long id;
    private String seats;
    private StationEntity currentStation;
}
