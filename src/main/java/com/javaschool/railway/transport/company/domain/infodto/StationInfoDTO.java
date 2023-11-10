package com.javaschool.railway.transport.company.domain.infodto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class StationInfoDTO {
    private Long id;
    private String name;
    private String city;
}
