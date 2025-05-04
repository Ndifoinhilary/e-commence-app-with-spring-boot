package com.bydefault.store.controller;
import com.bydefault.store.dtos.UserDto;
import com.bydefault.store.dtos.UserUpdateDto;
import com.bydefault.store.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user/")
public class UserController {
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserDto>> getUsers(@RequestParam(required = false, defaultValue = "", name = "name") String name) {

        return ResponseEntity.ok(userService.findAll(name));
    }

    @PostMapping("register/")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto, UriComponentsBuilder uriBuilder) {
        var user = userService.create(userDto);
        var uri = uriBuilder.path("/api/v1/user/{id}/").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @GetMapping("{id}/")
    public ResponseEntity<UserDto>getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PatchMapping("{id}/update/")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long id, @RequestBody UserUpdateDto updateDto) {
        return ResponseEntity.ok(userService.update(updateDto, id));
    }

    @DeleteMapping("{id}/")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
