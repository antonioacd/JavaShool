package com.javaschool.railway.transport.company.domain.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JwtGenerator is a component responsible for generating, parsing, and validating JWTs (JSON Web Tokens).
 */
@Component
public class JwtGenerator {


    private List<String> getRolesFromAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    /**
     * Generates a JWT based on the provided authentication information.
     *
     * @param authentication The authentication object containing user details.
     * @return The generated JWT.
     */
    public String generateToken(Authentication authentication) {
        String email = authentication.getName();
        List<String> roles = getRolesFromAuthorities(authentication.getAuthorities());

        return Jwts.builder()
                .setSubject(email)
                .claim("roles", roles)  // Agrega roles a las reclamaciones
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

    public List<String> getRolesFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        List<String> roles = (List<String>) claims.get("roles");

        return roles != null ? roles : Collections.emptyList();
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
