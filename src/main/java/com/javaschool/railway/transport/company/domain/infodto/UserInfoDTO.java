package com.javaschool.railway.transport.company.domain.infodto;

import lombok.NoArgsConstructor;
import lombok.Data;
import java.util.List;


@NoArgsConstructor
@Data
public class UserInfoDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private List<RoleInfoDTO> roles;
}
