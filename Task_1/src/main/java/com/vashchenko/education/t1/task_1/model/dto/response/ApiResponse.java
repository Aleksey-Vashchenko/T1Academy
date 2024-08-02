package com.vashchenko.education.t1.task_1.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import java.util.LinkedList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class ApiResponse<T> {
    Integer status = HttpStatus.OK.value();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    T data;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<ExtraErrorMessage> errorMessages = new LinkedList<>();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String error;

    public ApiResponse(T data) {
        this.data = data;
    }

    public ApiResponse(HttpStatus status) {
        this.status = status.value();
    }

    public ApiResponse(HttpStatus status, T data) {
        this.status = status.value();
        this.data = data;
    }
    public ApiResponse(HttpStatus status, String error) {
        this.status = status.value();
        this.error = error;
    }


    public void addError(ExtraErrorMessage errorMessage){
        errorMessages.add(errorMessage);
    }

    public void addErrors(List<ExtraErrorMessage> messages){
        errorMessages.addAll(messages);
    }

}
