package com.javaschool.railway.transport.company.domain.user;

import com.javaschool.railway.transport.company.domain.rol.RolEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Data
public class UserInfoDTO {
    private Integer id;
    private String name;
    private String surname;
    private LocalDate date_of_birth;
}
