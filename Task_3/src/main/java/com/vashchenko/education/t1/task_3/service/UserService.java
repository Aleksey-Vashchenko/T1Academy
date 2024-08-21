package com.vashchenko.education.t1.task_3.service;

import com.vashchenko.education.t1.task_3.exception.UserIsNotFoundException;
import com.vashchenko.education.t1.task_3.exception.WrongLoginOrPasswordException;
import com.vashchenko.education.t1.task_3.model.entity.User;
import com.vashchenko.education.t1.task_3.repository.JpaUserRepository;
import com.vashchenko.education.t1.task_3.web.dto.request.RegistrationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JpaUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(WrongLoginOrPasswordException::new);
    }

    User findById(UUID id){
        return userRepository.findById(id).orElseThrow(() -> new UserIsNotFoundException(id));
    }

    public void registration(RegistrationRequestDto requestDto) {
        User user = new User();
        user.setEmail(requestDto.email());
        user.setUsername(requestDto.username());
        user.setPasswordHash(passwordEncoder.encode(requestDto.password()));
        userRepository.save(user);
    }
}
