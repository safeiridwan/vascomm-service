package com.vascomm.controller.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "First name cannot be blank or null.")
    private String firstName;

    private String lastName;

    @Email(message = "Email pattern not valid.")
    @NotBlank(message = "Email cannot be blank or null.")
    private String email;

    @NotBlank(message = "Password cannot be blank or null.")
    private String password;
}
