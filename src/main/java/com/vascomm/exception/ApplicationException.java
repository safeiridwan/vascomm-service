package com.vascomm.exception;

import com.vascomm.response.ResponseAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static com.vascomm.util.constant.ResponseMessage.*;

@ControllerAdvice
public class ApplicationException {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationException.class);
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        if (ex instanceof MethodArgumentNotValidException er) {
            List<String> errors = new ArrayList<>();
            er.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));

            ResponseAPI error = new ResponseAPI();
            error.setCode(HttpStatus.BAD_REQUEST);
            error.setMessage(BAD_REQUEST);
            error.setError(errors);

            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        } else if (ex instanceof HttpMessageNotReadableException) {
            ResponseAPI error = new ResponseAPI();
            error.setCode(HttpStatus.BAD_REQUEST);
            error.setMessage(BAD_REQUEST);
            error.setError(null);

            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        } else if (ex instanceof HttpRequestMethodNotSupportedException methodNotSupportedEx) {
            String methodName = methodNotSupportedEx.getMethod();
            String message = "Request method '"+ methodName +"' not supported.";

            ResponseAPI error = new ResponseAPI();
            error.setCode(HttpStatus.METHOD_NOT_ALLOWED);
            error.setMessage(message);
            error.setError(null);

            return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
        } else {
            logger.error("An error occurred: ", ex);
            ResponseAPI error = new ResponseAPI();
            error.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
            error.setMessage(SERVER_ERROR);
            error.setError(null);

            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
