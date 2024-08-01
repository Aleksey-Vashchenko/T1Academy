package com.vashchenko.education.t1.task_1.exception;

public class OrderIsNotFoundException extends BaseNotFoundException {
    public OrderIsNotFoundException(String condition, Object data) {
        super(String.format("User with %s '%s' not found",condition,data.toString()));
    }
}
