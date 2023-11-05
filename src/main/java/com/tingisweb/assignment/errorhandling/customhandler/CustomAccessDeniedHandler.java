package com.tingisweb.assignment.errorhandling.customhandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A custom implementation of the Spring Security AccessDeniedHandler interface to handle access denied situations.
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * Handles access denied situations by sending an unauthorized response in JSON format.
     *
     * @param request              The HttpServletRequest for the denied request.
     * @param response             The HttpServletResponse to send the response.
     * @param accessDeniedException The AccessDeniedException that triggered the access denial.
     * @throws IOException      If an I/O error occurs during the response handling.
     * @throws ServletException If a servlet exception occurs.
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String json = "{\"statusCode\":401,\"timestamp\":\"" + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                "\",\"message\":\"Unauthorized action\"}";
        response.getOutputStream().println(json);
    }
}
