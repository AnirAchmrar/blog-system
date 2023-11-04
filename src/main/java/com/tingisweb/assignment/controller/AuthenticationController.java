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

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    private final SecurityService securityService;
    private final UserService userService;

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

@Data
class Credentials {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
