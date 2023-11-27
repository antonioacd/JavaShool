package com.javaschool.railway.transport.company.domain.infodto;

import com.javaschool.railway.transport.company.domain.security.SecurityConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AuthResponseDTO {

    private String accessToken;
    private static final String tokenType = SecurityConstants.TOKEN_TYPE;

    public String getTokenType() {
        return tokenType;
    }
}
