package com.vashchenko.education.t1.task_3;

import com.vashchenko.education.t1.task_3.exception.UserAlreadyExistsException;
import com.vashchenko.education.t1.task_3.service.UserService;
import com.vashchenko.education.t1.task_3.web.dto.request.RegistrationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity(securedEnabled = true,prePostEnabled = true)
@RequiredArgsConstructor
public class Task3Application {
    private final UserService userService;
    public static void main(String[] args) {
        SpringApplication.run(Task3Application.class, args);
    }

    @Profile("!test")
    @EventListener(ApplicationReadyEvent.class)
    public void applicationReady() throws UserAlreadyExistsException {
        RegistrationRequestDto requestDto = new RegistrationRequestDto("1234","1235","123");
        userService.registration(requestDto);
    }

}
