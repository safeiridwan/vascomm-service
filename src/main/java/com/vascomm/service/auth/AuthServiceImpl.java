package com.vascomm.service.auth;

import com.vascomm.controller.auth.request.LoginRequest;
import com.vascomm.controller.auth.request.RegisterRequest;
import com.vascomm.entity.User;
import com.vascomm.repository.UserRepository;
import com.vascomm.response.ResponseAPI;
import com.vascomm.util.JwtHelperUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.vascomm.util.constant.ResponseMessage.SERVER_ERROR;
import static java.util.Collections.emptyList;

import static com.vascomm.util.constant.Constant.ADMIN_ROLE;
import static com.vascomm.util.constant.ResponseMessage.OK;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService, UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtHelperUtil jwtUtil;

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
}
