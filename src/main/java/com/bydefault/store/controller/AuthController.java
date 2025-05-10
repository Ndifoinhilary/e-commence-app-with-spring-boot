package com.bydefault.store.controller;


import com.bydefault.store.dtos.user.JwtResponse;
import com.bydefault.store.dtos.user.LoginRequestDto;
import com.bydefault.store.dtos.user.PasswordUpdateDto;
import com.bydefault.store.dtos.user.UserDto;
import com.bydefault.store.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/auth/")
@AllArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("register/")
    @Operation(summary = "Create or register a new user")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto, UriComponentsBuilder uriBuilder) {
        var user = userService.create(userDto);
        var uri = uriBuilder.path("/api/v1/user/{id}/").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @PostMapping("login/")
    @Operation(summary = "End point to login a user and receive jwt token")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return ResponseEntity.ok(userService.login(loginRequestDto, response));
    }

    @PostMapping("change-password/")
    public ResponseEntity<String> changePassword( @RequestBody PasswordUpdateDto password) {
        return ResponseEntity.ok(userService.changePassword(password));
    }

    @PostMapping("refresh/")
    @Operation(summary = "Endpoint to generate refresh token")
    public ResponseEntity<JwtResponse> refreshToken(@CookieValue(value = "refresh_token") String token) {
        return ResponseEntity.ok(userService.refreshToken(token));
    }
}
