package com.tingisweb.assignment.mapper;

import com.tingisweb.assignment.dto.UserDto;
import com.tingisweb.assignment.entity.UserEntity;
import com.tingisweb.assignment.errorhandling.exception.ObjectNotFoundException;
import com.tingisweb.assignment.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class UserMapper implements GenericMapper<UserEntity, UserDto>{

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    public static final String USER_NOT_FOUND = "User not found!";
    @Override
    public UserDto toDto(UserEntity entity) {
        if (entity == null){
            return null;
        }
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setFirstname(entity.getFirstname());
        dto.setLastname(entity.getLastname());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setPassword(null);
        dto.setUsername(entity.getUsername());
        return dto;
    }

    @Override
    public UserEntity toEntity(UserDto dto) {
        if (dto == null){
            return null;
        }
        UserEntity entity;
        if(dto.getId() != null){
            entity = userRepository.findById(dto.getId()).orElseThrow(()->
                    new ObjectNotFoundException(USER_NOT_FOUND));
        }else {
            entity = new UserEntity();
        }
        entity.setId(dto.getId());
        entity.setFirstname(dto.getFirstname());
        entity.setLastname(entity.getLastname());
        entity.setPhone(entity.getPhone());
        entity.setEmail(entity.getEmail());
        String encryptedPassword = bCryptPasswordEncoder.encode(entity.getPassword());
        entity.setPassword(encryptedPassword);
        entity.setUsername(entity.getUsername());
        return entity;
    }

    @Override
    public Page<UserDto> toPageDto(Page<UserEntity> entities) {
        if(entities != null){
            return entities.map(this::toDto);
        }else {
            return Page.empty();
        }
    }

    @Override
    public List<UserDto> toListDto(List<UserEntity> entities) {
        if(entities != null){
            return entities.stream().map(this::toDto).toList();
        }else {
            return Collections.emptyList();
        }
    }
}
