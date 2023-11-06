package com.tingisweb.assignment.service;

import com.tingisweb.assignment.dto.UserDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * An interface for user-related operations.
 */
public interface UserService {

    /**
     * Find a user by their username.
     *
     * @param username The username of the user to find.
     * @return The DTO of the found user.
     * @throws UsernameNotFoundException If the user with the specified username is not found.
     * @throws IllegalArgumentException If the provided data is invalid or missing.
     */
    UserDto findUserByUsername(String username) throws UsernameNotFoundException, IllegalArgumentException;

}
