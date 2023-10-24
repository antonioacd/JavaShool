package com.javaschool.railway.transport.company.domain.infodto;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Data
@ToString
public class StationInfoDTO {
    private Long id;
    private String name;
    private String city;
}
