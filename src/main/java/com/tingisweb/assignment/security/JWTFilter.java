package com.tingisweb.assignment.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.tingisweb.assignment.service.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * A custom filter to handle JWT (JSON Web Token) authentication and authorization.
 */
@Component
@AllArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final UserServiceImpl userServiceImpl;
    private final JWTUtil jwtUtil;

    /**
     * Performs JWT token validation and authentication during the request processing.
     *
     * @param request      The HttpServletRequest for the incoming request.
     * @param response     The HttpServletResponse for sending responses.
     * @param filterChain  The FilterChain to continue the request processing.
     * @throws ServletException             If a servlet exception occurs.
     * @throws IOException                  If an I/O error occurs during the request processing.
     * @throws JWTVerificationException     If JWT token verification fails.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException,
            JWTVerificationException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")){
            String jwt = authHeader.substring(7);
            if (jwt.isBlank()){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid JWT Token in Bearer Header");
            }else {
                try {
                    String username = jwtUtil.validateTokenAndRetrieveSubject(jwt);
                    UserDetails userDetails = userServiceImpl.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(username,
                                    userDetails.getPassword(),userDetails.getAuthorities());
                    if (SecurityContextHolder.getContext().getAuthentication()==null){
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }catch (AccessDeniedException | UsernameNotFoundException | JWTVerificationException e){
                    // Failed to verify JWT
                    //Response is handled by CustomAuthenticationHandler class
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
