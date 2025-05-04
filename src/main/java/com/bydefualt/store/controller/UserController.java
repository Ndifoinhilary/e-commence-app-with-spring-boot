package com.bydefualt.store.controller;

import com.bydefualt.store.dtos.UserDto;
import com.bydefualt.store.entities.User;
import com.bydefualt.store.entities.mappers.UserMapper;
import com.bydefualt.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {


    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @GetMapping()
    public ResponseEntity<List<UserDto>> getUsers(@RequestParam(required = false, defaultValue = "", name = "name") String name) {
        if (!Set.of("name", "email").contains(name)) {
            name = "name";
        }
        List<User> users = userRepository.findAll(Sort.by(name).ascending());
        List<UserDto> userDtos = users.stream().map(userMapper::toDto).toList();
        return ResponseEntity.ok(userDtos);
    }
}
