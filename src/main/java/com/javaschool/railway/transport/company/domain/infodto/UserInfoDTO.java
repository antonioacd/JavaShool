package com.javaschool.railway.transport.company.domain.infodto;

import lombok.*;

import java.time.LocalDate;


@NoArgsConstructor
@Data
public class UserInfoDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private RolInfoDTO rol;
}
