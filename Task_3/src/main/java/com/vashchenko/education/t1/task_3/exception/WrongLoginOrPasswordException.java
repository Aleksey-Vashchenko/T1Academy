package com.vashchenko.education.t1.task_3.exception;

public class WrongLoginOrPasswordException extends RuntimeException{
    public WrongLoginOrPasswordException() {
        super("Wrong login or password");
    }
}
