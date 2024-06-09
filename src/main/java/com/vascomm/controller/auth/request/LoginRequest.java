package com.vascomm.controller.auth.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotNull(message = "Email cannot be blank or null.")
    private String email;
    @NotNull(message = "Password cannot be blank or null.")
    private String password;
}
