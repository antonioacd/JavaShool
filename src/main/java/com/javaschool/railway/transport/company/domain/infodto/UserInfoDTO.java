package com.javaschool.railway.transport.company.domain.infodto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;
import java.util.List;


@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class UserInfoDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private List<RoleInfoDTO> roles;
}
