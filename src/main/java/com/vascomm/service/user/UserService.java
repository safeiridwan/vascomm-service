package com.vascomm.service.user;

import com.vascomm.controller.auth.request.RegisterRequest;
import com.vascomm.controller.user.request.EditUserRequest;
import com.vascomm.response.ResponseAPI;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<ResponseAPI> editUser(String userId, EditUserRequest request);
}
