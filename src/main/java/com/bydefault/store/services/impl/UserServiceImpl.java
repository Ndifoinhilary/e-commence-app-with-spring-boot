package com.bydefault.store.services.impl;

import com.bydefault.store.config.JwtServices;
import com.bydefault.store.dtos.user.*;
import com.bydefault.store.entities.User;
import com.bydefault.store.entities.mappers.UserMapper;
import com.bydefault.store.exceptions.PasswordNotMatchException;
import com.bydefault.store.exceptions.ResourceNotFoundException;
import com.bydefault.store.repositories.UserRepository;
import com.bydefault.store.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtServices jwtServices;

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
        if(userRepository.existsByEmail(email)){
            throw new RuntimeException("User with email " + email + " already exists");
        }
        var user = userMapper.toEntity(userDto);
        var password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public void delete(Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        userRepository.delete(user);
    }

    @Override
    public String changePassword(PasswordUpdateDto passwordUpdateDto, Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        var userCurrentPassword = user.getPassword();
        var newPassword = passwordUpdateDto.getNewPassword();
        var confirmPassword = passwordUpdateDto.getConfirmPassword();
        if(!passwordEncoder.matches(passwordUpdateDto.getNewPassword(), userCurrentPassword)){
            throw new PasswordNotMatchException("You entered an incorrect password.");
        }
        if(!confirmPassword.equals(newPassword)) {
            throw new PasswordNotMatchException("New password and confirm password is incorrect");

        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return "Password changed successfully";
    }

    @Override
    public JwtResponse login(LoginRequestDto loginRequestDto) {
     authenticationManager.authenticate(
             new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
     );
     var token = jwtServices.generateJwtToken(loginRequestDto.getEmail());
     var jwtToken = new JwtResponse();
     jwtToken.setToken(token);
       return jwtToken;
    }
}
