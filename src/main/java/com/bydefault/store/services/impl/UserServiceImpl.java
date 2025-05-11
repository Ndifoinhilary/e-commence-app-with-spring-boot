package com.bydefault.store.services.impl;

import com.bydefault.store.config.JwtConfig;
import com.bydefault.store.config.JwtServices;
import com.bydefault.store.dtos.user.*;
import com.bydefault.store.entities.Role;
import com.bydefault.store.entities.User;
import com.bydefault.store.entities.mappers.UserMapper;
import com.bydefault.store.exceptions.PasswordNotMatchException;
import com.bydefault.store.exceptions.ResourceNotFoundException;
import com.bydefault.store.repositories.UserRepository;
import com.bydefault.store.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtServices jwtServices;
    private final JwtConfig jwtConfig;

    @Override
    public List<UserDto> findAll(String name) {
        if (!Set.of("name", "email").contains(name)) {
            name = "name";
        }
        List<User> users = userRepository.findAll(Sort.by(name).ascending());
        return users.stream().map(userMapper::toDto).toList();
    }

    @Override
    public UserDto findById(Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto update(UserUpdateDto updateDto, Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        user.setName(updateDto.getName());
        user.setEmail(updateDto.getEmail());
        var savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDto create(UserDto userDto) {
        var email = userDto.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("User with email " + email + " already exists");
        }
        var user = userMapper.toEntity(userDto);
        var password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public void delete(Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        userRepository.delete(user);
    }

    @Override
    public String changePassword(PasswordUpdateDto passwordUpdateDto) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = (Long) authentication.getPrincipal();
        var user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));
        var newPassword = passwordUpdateDto.getNewPassword();
        var confirmPassword = passwordUpdateDto.getConfirmPassword();
        if (!passwordEncoder.matches(passwordUpdateDto.getNewPassword(), user.getPassword())) {
            throw new PasswordNotMatchException("You entered an incorrect password.");
        }
        if (!confirmPassword.equals(newPassword)) {
            throw new PasswordNotMatchException("New password and confirm password is incorrect");

        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return "Password changed successfully";
    }

    @Override
    public JwtResponse login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );
        var user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User " + loginRequestDto.getEmail() + " not found"));
        var accessToken = jwtServices.generateAccessToken(user);
        var refreshToken = jwtServices.generateRefreshToken(user);
        var cookie = new Cookie("refresh_token", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/api/v1/auth/refresh/");
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration()); // expires in 30 days
        cookie.setSecure(true);
        response.addCookie(cookie);
        var jwtToken = new JwtResponse();
        jwtToken.setToken(accessToken);
        return jwtToken;
    }

    @Override
    public UserDto currentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = (Long) authentication.getPrincipal();
        var user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.toDto(user);
    }

    @Override
    public JwtResponse refreshToken(String refreshToken) {
        if (!jwtServices.validateJwtToken(refreshToken)) {
            throw new RuntimeException("Invalid JWT token");
        }
        var userId = jwtServices.getUserIdFromJwtToken(refreshToken);
        var user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        var accessToken = jwtServices.generateRefreshToken(user);
        var jwtToken = new JwtResponse();
        jwtToken.setToken(accessToken);
        return jwtToken;
    }
}
