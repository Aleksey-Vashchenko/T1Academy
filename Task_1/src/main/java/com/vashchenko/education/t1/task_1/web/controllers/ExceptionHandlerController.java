package com.vashchenko.education.t1.task_1.web.controllers;

import com.vashchenko.education.t1.task_1.exception.BaseBadRequestException;
import com.vashchenko.education.t1.task_1.exception.BaseConflictException;
import com.vashchenko.education.t1.task_1.exception.BaseNotFoundException;
import com.vashchenko.education.t1.task_1.model.dto.response.ApiResponse;
import com.vashchenko.education.t1.task_1.model.dto.response.ExtraErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(BaseNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse> notFoundException(final BaseNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(new ApiResponse(status,e.getMessage()), status);
    }

    @ExceptionHandler(BaseConflictException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse> baseConflictException(final BaseConflictException e) {
        HttpStatus status = HttpStatus.CONFLICT;
        return new ResponseEntity<>(new ApiResponse(status,e.getMessage()), status);
    }

    @ExceptionHandler(BaseBadRequestException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse> baseBadRequestException(final BaseBadRequestException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiResponse<Object> apiResponse = new ApiResponse<>(status,"Bad request");
        apiResponse.addErrors(e.getErrors());
        return new ResponseEntity<>(apiResponse,status);
    }
}
