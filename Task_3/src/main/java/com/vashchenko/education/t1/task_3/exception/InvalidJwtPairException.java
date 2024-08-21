package com.vashchenko.education.t1.task_3.exception;

public class InvalidJwtPairException extends RuntimeException{
    public InvalidJwtPairException() {
        super("Invalid jwt pair");
    }
}
