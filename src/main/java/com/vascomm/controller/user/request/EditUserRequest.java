package com.vascomm.controller.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditUserRequest {
    private String firstName;
    private String lastName;
}
