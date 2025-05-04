package com.bydefualt.store.entities.mappers;

import com.bydefualt.store.dtos.UserDto;
import com.bydefualt.store.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
