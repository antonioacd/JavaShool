package com.javaschool.railway.transport.company.domain.infodto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class AuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer ";

    public AuthResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}
