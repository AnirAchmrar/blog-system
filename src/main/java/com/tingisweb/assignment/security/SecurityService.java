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

/**
 * A service class responsible for handling user authentication and authorization.
 */
@Service
@AllArgsConstructor
public class SecurityService {

    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    public static final String WRONG_CREDENTIALS = "Wrong credentials";
    public static final String USER_NOT_FOUND = "User not found!";

    /**
     * Logs in a user by validating their username and password and generating a JWT token.
     *
     * @param username The username of the user to log in.
     * @param password The user's password.
     * @return The generated JWT token upon successful login.
     * @throws UsernameNotFoundException If the provided credentials are invalid or the user is not found.
     */
    public String login(String username, String password) throws UsernameNotFoundException{
        if (username != null && password != null){
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(username,password);

            authenticationManager.authenticate(authInputToken);

            return jwtUtil.generateToken(username);
        }else {
            throw new UsernameNotFoundException(WRONG_CREDENTIALS);
        }
    }

    /**
     * Retrieves the currently authenticated user entity.
     *
     * @return The UserEntity representing the currently authenticated user.
     * @throws UsernameNotFoundException If the user is not found or the user is not authenticated.
     */
    public UserEntity getAuthenticatedUser() throws UsernameNotFoundException{
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
