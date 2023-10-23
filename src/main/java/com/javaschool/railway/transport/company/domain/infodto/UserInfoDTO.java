package com.javaschool.railway.transport.company.domain.infodto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Data
@ToString
public class UserInfoDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private LocalDate dateOfBirth;
    private RolInfoDTO rol;
}
