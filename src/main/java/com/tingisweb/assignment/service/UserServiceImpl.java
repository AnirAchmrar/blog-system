package com.tingisweb.assignment.service;

import com.tingisweb.assignment.dto.UserDto;
import com.tingisweb.assignment.entity.UserEntity;
import com.tingisweb.assignment.mapper.UserMapper;
import com.tingisweb.assignment.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;

/**
 * A service class that implements the UserDetailsService and UserService interfaces for managing user-related operations.
 */
@Service
@AllArgsConstructor
@Validated
public class UserServiceImpl implements UserDetailsService, UserService{

    public static final String NULL_USERNAME = "Null username";
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    public static final String USER_NOT_FOUND = "User not found!";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username != null){
            UserEntity user =  userRepository.findUserByUsername(username).orElseThrow(()->new UsernameNotFoundException(USER_NOT_FOUND));
            return new User(username,user.getPassword(),new ArrayList<>());
        }else {
            throw new IllegalArgumentException(NULL_USERNAME);
        }
    }

    @Override
    public UserDto findUserByUsername(String username) throws UsernameNotFoundException, IllegalArgumentException {
        if (username != null){
            return userMapper.toDto(userRepository.findUserByUsername(username).orElseThrow(()->new UsernameNotFoundException(USER_NOT_FOUND)));
        }else {
            throw new IllegalArgumentException(NULL_USERNAME);
        }
    }

}
