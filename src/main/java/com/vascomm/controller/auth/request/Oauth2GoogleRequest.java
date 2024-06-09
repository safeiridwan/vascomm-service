package com.vascomm.controller.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Oauth2GoogleRequest {
    @NotBlank(message = "Token must not be blank or null.")
    private String token;
}
