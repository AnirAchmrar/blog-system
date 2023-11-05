package com.tingisweb.assignment.errorhandling.customhandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A custom implementation of the Spring Security AuthenticationEntryPoint interface to handle unauthenticated requests.
 */
@Component
public class CustomAuthenticationHandler implements AuthenticationEntryPoint {

    /**
     * Commences the handling of unauthenticated requests by sending an unauthorized response in JSON format.
     *
     * @param request        The HttpServletRequest for the unauthenticated request.
     * @param response       The HttpServletResponse to send the response.
     * @param authException  The AuthenticationException that indicates unauthenticated access.
     * @throws IOException      If an I/O error occurs during the response handling.
     * @throws ServletException If a servlet exception occurs.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String json = "{\"statusCode\":401,\"timestamp\":\"" + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                "\",\"message\":\"Unauthenticated\"}";
        response.getOutputStream().println(json);
    }
}
