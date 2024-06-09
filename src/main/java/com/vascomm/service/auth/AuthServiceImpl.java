package com.vascomm.service.auth;

import com.vascomm.controller.auth.request.LoginRequest;
import com.vascomm.controller.auth.request.Oauth2GoogleRequest;
import com.vascomm.controller.auth.request.RegisterRequest;
import com.vascomm.entity.User;
import com.vascomm.repository.UserRepository;
import com.vascomm.response.ResponseAPI;
import com.vascomm.util.JwtHelperUtil;
import com.vascomm.util.oauth2.GoogleUtil;
import com.vascomm.util.oauth2.Oauth2GoogleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.util.Collections.emptyList;

import static com.vascomm.util.constant.Constant.ADMIN_ROLE;
import static com.vascomm.util.constant.ResponseMessage.OK;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService, UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtHelperUtil jwtUtil;
    private final GoogleUtil googleUtil;

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

    @Override
    public ResponseEntity<ResponseAPI> registrationAdmin(RegisterRequest request) {
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
        user.setRole(ADMIN_ROLE);


        userRepository.save(user);

        return new ResponseEntity<>(new ResponseAPI(200, OK, null, null), HttpStatus.OK);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPassword(), emptyList());
    }

    @Override
    public ResponseEntity<ResponseAPI> login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            return new ResponseEntity<>(new ResponseAPI(400, "User not found.", null, null), HttpStatus.BAD_REQUEST);
        }

        BCryptPasswordEncoder b = new BCryptPasswordEncoder();
        if (!b.matches(request.getPassword(), user.getPassword())) {
            return new ResponseEntity<>(new ResponseAPI(401, "Username or password invalid.", null, null), HttpStatus.UNAUTHORIZED);
        }

        Map<String, String> res = new HashMap<>();
        res.put("token", jwtUtil.generateToken(user.getUserId()));
        return new ResponseEntity<>(new ResponseAPI(200, OK, null, res), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseAPI> oauth2Google(Oauth2GoogleRequest request) {
        Oauth2GoogleResponse googlePayload = googleUtil.getGooglePayload(request.getToken());
        User user = userRepository.findByEmail(googlePayload.getEmail());
        if (user == null) {
            user = new User();
            user.setUserId(UUID.randomUUID().toString());
            user.setFirstName(googlePayload.getFirstName());
            user.setLastName(googlePayload.getLastName());
            user.setEmail(googlePayload.getEmail());
            user.setUserStatus(Boolean.TRUE);
        }

        user.setUpdatedBy(googlePayload.getFirstName());
        userRepository.save(user);

        Map<String, String> res = new HashMap<>();
        res.put("token", jwtUtil.generateToken(user.getUserId()));
        return new ResponseEntity<>(new ResponseAPI(200, OK, null, res), HttpStatus.OK);
    }
}
