package com.javaschool.railway.transport.company.domain.infodto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginDTO {
    private String email;
    private String password;
}
