package com.vascomm.service.auth;

import com.vascomm.controller.auth.request.RegisterRequest;
import com.vascomm.entity.User;
import com.vascomm.repository.UserRepository;
import com.vascomm.response.ResponseAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.vascomm.util.constant.ResponseMessage.OK;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ResponseEntity<ResponseAPI> registrationUser(RegisterRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user != null) {
            return new ResponseEntity<>(new ResponseAPI(400, "User already Exist", null, null), HttpStatus.BAD_REQUEST);
        }

        user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setUserStatus(Boolean.TRUE);

        userRepository.save(user);

        return new ResponseEntity<>(new ResponseAPI(200, OK, null, null), HttpStatus.OK);
    }

}
