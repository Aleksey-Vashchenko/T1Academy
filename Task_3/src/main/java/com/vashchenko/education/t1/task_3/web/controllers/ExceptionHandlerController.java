package com.vashchenko.education.t1.task_3.web.controllers;

import com.vashchenko.education.t1.task_3.exception.UserIsNotFoundException;
import com.vashchenko.education.t1.task_3.exception.WrongLoginOrPasswordException;
import com.vashchenko.education.t1.task_3.web.dto.response.ErrorResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

   @ExceptionHandler
    public ResponseEntity<ErrorResponse> userIsNotFoundException(final UserIsNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> jwtException(final JwtException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> wrongLoginOrPasswordException(final WrongLoginOrPasswordException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}
