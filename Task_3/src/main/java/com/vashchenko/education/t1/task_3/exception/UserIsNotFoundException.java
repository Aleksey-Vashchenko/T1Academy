package com.vashchenko.education.t1.task_3.exception;

import java.util.UUID;

public class UserIsNotFoundException extends RuntimeException{
    public UserIsNotFoundException(UUID uuid) {
        super("User with id "+uuid+" was not found");
    }
}
