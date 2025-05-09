package com.bydefault.store.controller;

import com.bydefault.store.dtos.PasswordUpdateDto;
import com.bydefault.store.dtos.UserDto;
import com.bydefault.store.dtos.UserUpdateDto;
import com.bydefault.store.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user/")
@Tag(name = "User")
public class UserController {
    private final UserService userService;

    @GetMapping()
    @Operation(summary = "Give a list of users in the system")
    public ResponseEntity<List<UserDto>> getUsers(@RequestParam(required = false, defaultValue = "", name = "name") String name) {

        return ResponseEntity.ok(userService.findAll(name));
    }

    @PostMapping("register/")
    @Operation(summary = "Create or register a new user")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto, UriComponentsBuilder uriBuilder) {
        var user = userService.create(userDto);
        var uri = uriBuilder.path("/api/v1/user/{id}/").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @GetMapping("{id}/")
    @Operation(summary = "Get a user details by passing the user Id .")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PatchMapping("{id}/update/")
    @Operation(summary = "update a user from the system", method = "PATCH")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long id, @RequestBody UserUpdateDto updateDto) {
        return ResponseEntity.ok(userService.update(updateDto, id));
    }

    @DeleteMapping("{id}/")
    @Operation(summary = "Delete a user from the system")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("{id}/change-password/")
    public ResponseEntity<String> changePassword(@PathVariable("id") Long id, @RequestBody PasswordUpdateDto password) {
        return ResponseEntity.ok(userService.changePassword(password, id));
    }
}
