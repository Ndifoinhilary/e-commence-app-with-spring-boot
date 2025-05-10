package com.bydefault.store.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(message = "It should be more then 4 characters.", min = 4, max = 255)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email.")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 25, message = "Your password should be between 6 to 25 characters long.")
    private String password;
}


