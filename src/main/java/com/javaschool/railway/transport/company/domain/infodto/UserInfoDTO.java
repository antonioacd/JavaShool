package com.javaschool.railway.transport.company.domain.infodto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserInfoDTO {
    private Integer id;
    private String name;
    private String surname;
    private LocalDate date_of_birth;
}
