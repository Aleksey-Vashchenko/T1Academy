package com.vashchenko.education.t1.task_3.security;

import com.vashchenko.education.t1.task_3.exception.WrongLoginOrPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordValidator {
    private final PasswordEncoder passwordEncoder;

    public void validatePassword(String rawPassword, String encodedPassword) throws WrongLoginOrPasswordException{
        boolean checkResult = passwordEncoder.matches(rawPassword,encodedPassword);
        if(!checkResult){
            throw new WrongLoginOrPasswordException();
        }
    }
}
