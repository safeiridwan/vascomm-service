package com.vascomm.service.user;

import com.vascomm.controller.auth.request.RegisterRequest;
import com.vascomm.controller.user.request.EditUserRequest;
import com.vascomm.controller.user.response.EditUserResponse;
import com.vascomm.entity.User;
import com.vascomm.repository.UserRepository;
import com.vascomm.response.ResponseAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.vascomm.util.constant.ResponseMessage.OK;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public ResponseEntity<ResponseAPI> editUser(String userId, EditUserRequest request) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return new ResponseEntity<>(new ResponseAPI(400, "User not found", null, null), HttpStatus.BAD_REQUEST);
        }

        if (request.getFirstName() != null && !request.getFirstName().isEmpty()) {
            user.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null && !request.getLastName().isEmpty()) {
            user.setLastName(request.getLastName());
        }

        userRepository.save(user);

        EditUserResponse res = new EditUserResponse();
        res.generate(user);

        return new ResponseEntity<>(new ResponseAPI(200, OK, null, res), HttpStatus.OK);
    }
}
