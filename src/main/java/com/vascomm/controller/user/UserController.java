package com.vascomm.controller.user;

import com.vascomm.controller.auth.request.RegisterRequest;
import com.vascomm.controller.user.request.EditUserRequest;
import com.vascomm.response.ResponseAPI;
import com.vascomm.service.user.UserService;
import com.vascomm.util.PageInput;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {
    private final UserService service;

    @PutMapping("/{user_id}")
    public ResponseEntity<ResponseAPI> editUser(
            @PathVariable(name = "user_id") String userId,
            @Valid @RequestBody EditUserRequest request) {
        return service.editUser(userId, request);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<ResponseAPI> detailUser(@PathVariable(name = "user_id") String userId) {
        return service.detailUser(userId);
    }

    @GetMapping()
    public ResponseEntity<ResponseAPI> listUser(PageInput pageInput) {
        return service.listUser(pageInput);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<ResponseAPI> deleteUser(@PathVariable(name = "user_id") String userId) {
        return service.deleteUser(userId);
    }

}
