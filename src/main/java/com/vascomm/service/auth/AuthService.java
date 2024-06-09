package com.vascomm.service.auth;

import com.vascomm.controller.auth.request.LoginRequest;
import com.vascomm.controller.auth.request.Oauth2GoogleRequest;
import com.vascomm.controller.auth.request.RegisterRequest;
import com.vascomm.response.ResponseAPI;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<ResponseAPI> registrationUser(RegisterRequest request);
    ResponseEntity<ResponseAPI> registrationAdmin(RegisterRequest request);
    ResponseEntity<ResponseAPI> login(LoginRequest request);
    ResponseEntity<ResponseAPI> oauth2Google(Oauth2GoogleRequest request);

}
