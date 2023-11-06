package com.tingisweb.assignment.service;

import com.tingisweb.assignment.dto.UserDto;
import com.tingisweb.assignment.entity.UserEntity;
import com.tingisweb.assignment.mapper.UserMapper;
import com.tingisweb.assignment.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * This test class contains unit tests for the UserServiceImpl class, which is responsible for handling user-related
 * operations.
 */
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRepository userRepository;
    private static final String EXISTING_USERNAME = "existingUser";
    private static final String NON_EXISTING_USERNAME = "nonExistingUser";
    public static final String NULL_USERNAME = "Null username";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test case to verify that when attempting to load a user by a null username,
     * an IllegalArgumentException is thrown. Loading a user with a null username is not allowed.
     */
    @Test
    void loadUserByUsername_Ko_NullUsername() {
        // Verify that attempting to load a user with a null username results in an IllegalArgumentException.
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> userServiceImpl.loadUserByUsername(null)
        );

        // Verify that the exception message contains the expected message indicating a null username.
        assertTrue(thrown.getMessage().contains(NULL_USERNAME));
    }

    /**
     * Test case to verify that when attempting to load a user by a non-existing username,
     * a UsernameNotFoundException is thrown, indicating that the user does not exist.
     */
    @Test
    void loadUserByUsername_Ko_UserNotFound() {
        // Mock the UserRepository to throw a UsernameNotFoundException when trying to find a user by a non-existing username.
        when(userRepository.findUserByUsername(NON_EXISTING_USERNAME))
                .thenThrow((UsernameNotFoundException.class));

        // Verify that attempting to load a user with a non-existing username results in a UsernameNotFoundException.
        assertThrows(UsernameNotFoundException.class,
                () -> userServiceImpl.loadUserByUsername(NON_EXISTING_USERNAME));
    }

    /**
     * Test case to verify that when attempting to load a user by an existing username,
     * the user details are retrieved successfully.
     */
    @Test
    void loadUserByUsername_Ok_UserFound() {
        // Create a mock UserEntity with the existing username and a password.
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(EXISTING_USERNAME);
        userEntity.setPassword("password");

        // Mock the UserRepository to return an Optional containing the user entity when finding the user by the existing username.
        when(userRepository.findUserByUsername(EXISTING_USERNAME))
                .thenReturn(Optional.of(userEntity));

        // Attempt to load the user details by the existing username.
        UserDetails userDetails = userServiceImpl.loadUserByUsername(EXISTING_USERNAME);

        // Assertions to validate the result.
        assertNotNull(userDetails);
        assertEquals(EXISTING_USERNAME, userDetails.getUsername());
    }

    /**
     * Test case to verify that when attempting to find a user by a null username,
     * an IllegalArgumentException is thrown. Finding a user with a null username is not allowed.
     */
    @Test
    void findUserByUsername_Ko_NullUsername() {
        // Verify that attempting to find a user with a null username results in an IllegalArgumentException.
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> userServiceImpl.findUserByUsername(null)
        );

        // Verify that the exception message contains the expected message indicating a null username.
        assertTrue(thrown.getMessage().contains(NULL_USERNAME));
    }

    /**
     * Test case to verify that when attempting to find a user by a non-existing username,
     * a UsernameNotFoundException is thrown, indicating that the user does not exist.
     */
    @Test
    void findUserByUsername_Ko_UserNotFound() {
        // Mock the UserRepository to throw a UsernameNotFoundException when trying to find a user by a non-existing username.
        when(userRepository.findUserByUsername(NON_EXISTING_USERNAME))
                .thenThrow((UsernameNotFoundException.class));

        // Verify that attempting to find a user with a non-existing username results in a UsernameNotFoundException.
        assertThrows(UsernameNotFoundException.class,
                () -> userServiceImpl.findUserByUsername(NON_EXISTING_USERNAME));
    }

    /**
     * Test case to verify that when attempting to find a user by an existing username,
     * the user is successfully retrieved and mapped to a UserDto.
     */
    @Test
    void findUserByUsername_Ok_UserFound() {
        // Create a mock UserEntity with the existing username and a password.
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(EXISTING_USERNAME);
        userEntity.setPassword("password");

        // Mock the UserRepository to return an Optional containing the user entity when finding the user by the existing username.
        when(userRepository.findUserByUsername(EXISTING_USERNAME)).
                thenReturn(Optional.of(userEntity));

        // Create a mock UserDto with the same username as the existing user.
        UserDto userDto = new UserDto();
        userDto.setUsername(EXISTING_USERNAME);

        // Mock the UserMapper to return the mock UserDto when mapping the UserEntity.
        when(userMapper.toDto(userEntity))
                .thenReturn(userDto);

        // Attempt to find the user by the existing username.
        UserDto foundUser = userServiceImpl.findUserByUsername(EXISTING_USERNAME);

        // Assertions to validate the result.
        assertNotNull(foundUser);
        assertEquals(EXISTING_USERNAME, foundUser.getUsername());
    }
}