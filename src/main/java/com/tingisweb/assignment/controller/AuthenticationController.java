package com.tingisweb.assignment.controller;

import com.tingisweb.assignment.dto.UserDto;
import com.tingisweb.assignment.security.SecurityService;
import com.tingisweb.assignment.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a REST controller that handle authentication requests.
 * It is exposed with the endpoint '/auth'.
 * This endpoint access is permitted to all clients.
 */
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final SecurityService securityService;
    private final UserService userService;

    /**
     * This endpoint is called by a POST request to log in a user by posting his credentials
     * (username and password).
     *
     * @param credentials the user credentials that are the username and password.
     * @return for a successful login the endpoint respond with an access token and the requesting
     * user general data with an OK http status. Otherwise, an UsernameNotFoundException is thrown that
     * trigger the CustomAuthenticationHandler to handle the response and return an Unauthenticated
     * error message with UNAUTHORIZED http status.
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> login(@RequestBody @Valid Credentials credentials){
        Map<String, Object> response = new HashMap<>();
        String token = securityService.login(credentials.getUsername(),credentials.getPassword());
        UserDto userDto = userService.findUserByUsername(credentials.getUsername());
        response.put("access_token",token);
        response.put("user",userDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

/**
 * A class that wrap the user credentials, username and password. Also, it takes care of data validation.
 */
@Data
class Credentials {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
