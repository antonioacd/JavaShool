package com.javaschool.railway.transport.company.domain.infodto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class RoleInfoDTO {
    private Long id;
    private String name;
}
