package com.bydefault.store.services;

import com.bydefault.store.dtos.user.*;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface UserService {
    List<UserDto> findAll(String name);
    UserDto findById(Long id);
    UserDto update(UserUpdateDto updateDto, Long id);
    UserDto create(UserDto userDto);
    void delete(Long id);
    String changePassword(PasswordUpdateDto passwordUpdateDto);
    JwtResponse login(LoginRequestDto loginRequestDto, HttpServletResponse response);
    UserDto currentUser();
    JwtResponse refreshToken(String refreshToken);


}
