package com.vascomm.controller;

import com.vascomm.response.ResponseAPI;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import static com.vascomm.util.constant.ResponseCode.OK;

@RestController
@RequestMapping()
public class ApplicationController {
    @GetMapping("health-check")
    public ResponseEntity<ResponseAPI> vascomApplication() {
        Map<String, Object> out = new HashMap<>();

        out.put("service", "vascomm-service");
        out.put("Time", new Date().toString());

        return new ResponseEntity<>(new ResponseAPI(200, OK, null, out), HttpStatus.OK);
    }

}
