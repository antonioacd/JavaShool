package com.javaschool.railway.transport.company.domain.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;

/**
 * JwtGenerator is a component responsible for generating, parsing, and validating JWTs (JSON Web Tokens).
 */
@Component
public class JwtGenerator {

    /**
     * Generates a JWT based on the provided authentication information.
     *
     * @param authentication The authentication object containing user details.
     * @return The generated JWT.
     */
    public String generateToken(Authentication authentication) {
        String email = authentication.getName();

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SECRET)
                .compact();
    }

    /**
     * Retrieves the username (subject) from the provided JWT.
     *
     * @param token The JWT to parse.
     * @return The username extracted from the JWT.
     */
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    /**
     * Validates the provided JWT.
     *
     * @param token The JWT to validate.
     * @return True if the JWT is valid; otherwise, an exception is thrown.
     * @throws AuthenticationCredentialsNotFoundException If the JWT is expired or incorrect.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SecurityConstants.JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }
}
