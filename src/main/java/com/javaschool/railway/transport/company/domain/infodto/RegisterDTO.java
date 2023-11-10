package com.javaschool.railway.transport.company.domain.infodto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String email;
    private String password;
    private String name;
    private String surname;
}
