package com.javaschool.railway.transport.company.domain.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * JwtAuthEntryPoint is a custom entry point for handling unauthorized requests.
 * It is invoked when a user attempts to access a protected resource without proper authentication.
 */
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    /**
     * Commences the authentication entry point, sending an unauthorized error response to the client.
     *
     * @param request       The HTTP request.
     * @param response      The HTTP response.
     * @param authException The authentication exception that triggered the entry point.
     * @throws IOException      If an I/O error occurs during the response sending.
     * @throws ServletException If an error occurs during the entry point handling.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
