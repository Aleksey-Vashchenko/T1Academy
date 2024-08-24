package com.vashchenko.education.t1.task_3.exception;

public class UserAlreadyExistsException extends BaseConflictException{
    public UserAlreadyExistsException(String email) {
        super(String.format("User with email %s already exists",email));
    }
}
