package com.javaschool.railway.transport.company.domain.infodto;

import jakarta.persistence.Column;
import lombok.*;

@NoArgsConstructor
@Data
public class StationInfoDTO {
    private Long id;
    private String name;
    private String city;
}
