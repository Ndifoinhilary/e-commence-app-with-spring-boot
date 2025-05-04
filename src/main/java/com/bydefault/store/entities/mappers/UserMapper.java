package com.bydefault.store.entities.mappers;

import com.bydefault.store.dtos.UserDto;
import com.bydefault.store.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
