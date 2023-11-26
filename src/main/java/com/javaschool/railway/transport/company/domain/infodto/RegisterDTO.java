package com.javaschool.railway.transport.company.domain.infodto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    private String email;
    private String password;
    private String name;
    private String surname;
}
