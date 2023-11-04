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

class UserServiceTest {

    @InjectMocks
    private UserService userService;
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

    @Test
    void loadUserByUsername_Ko_NullUsername() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> userService.loadUserByUsername(null)
        );
        assertTrue(thrown.getMessage().contains(NULL_USERNAME));
    }

    @Test
    void loadUserByUsername_Ko_UserNotFound() {
        when(userRepository.findUserByUsername(NON_EXISTING_USERNAME))
                .thenThrow((UsernameNotFoundException.class));

        assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername(NON_EXISTING_USERNAME));
    }

    @Test
    void loadUserByUsername_Ok_UserFound() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(EXISTING_USERNAME);
        userEntity.setPassword("password");

        when(userRepository.findUserByUsername(EXISTING_USERNAME))
                .thenReturn(Optional.of(userEntity));

        UserDetails userDetails = userService.loadUserByUsername(EXISTING_USERNAME);

        assertNotNull(userDetails);
        assertEquals(EXISTING_USERNAME, userDetails.getUsername());
    }

    @Test
    void findUserByUsername_Ko_NullUsername() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> userService.findUserByUsername(null)
        );
        assertTrue(thrown.getMessage().contains(NULL_USERNAME));
    }

    @Test
    void findUserByUsername_Ko_UserNotFound() {
        when(userRepository.findUserByUsername(NON_EXISTING_USERNAME))
                .thenThrow((UsernameNotFoundException.class));

        assertThrows(UsernameNotFoundException.class,
                () -> userService.findUserByUsername(NON_EXISTING_USERNAME));
    }

    @Test
    void findUserByUsername_Ok_UserFound() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(EXISTING_USERNAME);
        userEntity.setPassword("password");

        when(userRepository.findUserByUsername(EXISTING_USERNAME)).
                thenReturn(Optional.of(userEntity));

        UserDto userDto = new UserDto();
        userDto.setUsername(EXISTING_USERNAME);

        when(userMapper.toDto(userEntity))
                .thenReturn(userDto);

        UserDto foundUser = userService.findUserByUsername(EXISTING_USERNAME);

        assertNotNull(foundUser);
        assertEquals(EXISTING_USERNAME, foundUser.getUsername());
    }
}