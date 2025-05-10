package com.bydefault.store.services;

import com.bydefault.store.dtos.user.*;

import java.util.List;

public interface UserService {
    List<UserDto> findAll(String name);
    UserDto findById(Long id);
    UserDto update(UserUpdateDto updateDto, Long id);
    UserDto create(UserDto userDto);
    void delete(Long id);
    String changePassword(PasswordUpdateDto passwordUpdateDto, Long id);
    JwtResponse login(LoginRequestDto loginRequestDto);
    UserDto currentUser();


}
