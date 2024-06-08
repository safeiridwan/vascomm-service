package com.vascomm.controller;

import com.vascomm.response.ResponseAPI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.vascomm.util.constant.ResponseMessage.OK;

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
