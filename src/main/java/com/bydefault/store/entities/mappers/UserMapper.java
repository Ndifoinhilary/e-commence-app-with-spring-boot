package com.bydefault.store.entities.mappers;

import com.bydefault.store.dtos.user.LoginRequestDto;
import com.bydefault.store.dtos.user.UserDto;
import com.bydefault.store.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
    User toEntity(LoginRequestDto loginRequestDto);
}
