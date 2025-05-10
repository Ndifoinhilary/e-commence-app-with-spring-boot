package com.bydefault.store.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PasswordUpdateDto {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String currentPassword;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String newPassword;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String confirmPassword;
}
