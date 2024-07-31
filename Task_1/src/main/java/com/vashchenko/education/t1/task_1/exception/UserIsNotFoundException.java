package com.vashchenko.education.t1.task_1.exception;

import java.util.UUID;

public class UserIsNotFoundException extends BaseNotFoundException{
    public UserIsNotFoundException(String condition, Object data) {
        super(String.format("User with %s '%s' not found",condition,data.toString()));
    }
}
