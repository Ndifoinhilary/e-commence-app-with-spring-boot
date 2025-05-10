package com.bydefault.store.services;

import com.bydefault.store.dtos.user.LoginRequestDto;
import com.bydefault.store.dtos.user.PasswordUpdateDto;
import com.bydefault.store.dtos.user.UserDto;
import com.bydefault.store.dtos.user.UserUpdateDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAll(String name);
    UserDto findById(Long id);
    UserDto update(UserUpdateDto updateDto, Long id);
    UserDto create(UserDto userDto);
    void delete(Long id);
    String changePassword(PasswordUpdateDto passwordUpdateDto, Long id);
    String login(LoginRequestDto loginRequestDto);


}
