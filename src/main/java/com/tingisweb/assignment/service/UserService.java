package com.tingisweb.assignment.service;

import com.tingisweb.assignment.dto.UserDto;
import com.tingisweb.assignment.entity.UserEntity;
import com.tingisweb.assignment.errorhandling.exception.ObjectNotFoundException;
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

@Service
@AllArgsConstructor
@Validated
public class UserService implements UserDetailsService {

    public static final String NULL_USERNAME = "Null username";
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    public static final String USER_NOT_FOUND = "User not found!";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username != null){
            UserEntity user =  userRepository.findUserByUsername(username).orElseThrow(()->new ObjectNotFoundException(USER_NOT_FOUND));
            return new User(username,user.getPassword(),new ArrayList<>());
        }else {
            throw new IllegalArgumentException(NULL_USERNAME);
        }
    }

    public UserDto findUserByUsername(String username){
        if (username != null){
            return userMapper.toDto(userRepository.findUserByUsername(username).orElseThrow(()->new ObjectNotFoundException(USER_NOT_FOUND)));
        }else {
            throw new IllegalArgumentException(NULL_USERNAME);
        }
    }

}
