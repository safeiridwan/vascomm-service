package com.vascomm.service.user;

import com.vascomm.controller.user.request.EditUserRequest;
import com.vascomm.response.ResponseAPI;
import com.vascomm.util.PageInput;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<ResponseAPI> editUser(String userId, EditUserRequest request);
    ResponseEntity<ResponseAPI> detailUser(String userId);
    ResponseEntity<ResponseAPI> listUser(PageInput pageInput);
    ResponseEntity<ResponseAPI> deleteUser(String userId);
}
