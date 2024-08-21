package com.vashchenko.education.t1.task_3.exception;

import io.jsonwebtoken.JwtException;

public class InvalidJwtPairException extends JwtException {
    public InvalidJwtPairException() {
        super("Invalid jwt pair");
    }
}
