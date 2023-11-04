package com.tingisweb.assignment.security;

import com.tingisweb.assignment.entity.UserEntity;
import com.tingisweb.assignment.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecurityService {

    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    public static final String WRONG_CREDENTIALS = "Wrong credentials";
    public static final String USER_NOT_FOUND = "User not found!";

    public String login(String username, String password){
        if (username != null && password != null){
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(username,password);

            authenticationManager.authenticate(authInputToken);

            return jwtUtil.generateToken(username);
        }else {
            throw new UsernameNotFoundException(WRONG_CREDENTIALS);
        }
    }

    public UserEntity getAuthenticatedUser() {
        String currentUserName = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            currentUserName = authentication.getName();
        }
        return userRepository.findUserByUsername(currentUserName).orElseThrow(() ->
                new UsernameNotFoundException(USER_NOT_FOUND)
        );
    }

}
