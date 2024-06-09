package com.vascomm.controller.auth;

import com.vascomm.controller.auth.request.LoginRequest;
import com.vascomm.controller.auth.request.Oauth2GoogleRequest;
import com.vascomm.controller.auth.request.RegisterRequest;
import com.vascomm.response.ResponseAPI;
import com.vascomm.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthController {
    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<ResponseAPI> registrationUser(@Valid @RequestBody RegisterRequest request) {
        return service.registrationUser(request);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<ResponseAPI> registrationAdmin(@Valid @RequestBody RegisterRequest request) {
        return service.registrationAdmin(request);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseAPI> login(@Valid @RequestBody LoginRequest request) {
        return service.login(request);
    }

    @PostMapping("/oauth2/google")
    public ResponseEntity<ResponseAPI> oauth2Google(@Valid @RequestBody Oauth2GoogleRequest request) {
        return service.oauth2Google(request);
    }

}
