package com.vashchenko.education.t1.task_3.web.controllers;

import com.vashchenko.education.t1.task_3.security.UserPrincipal;
import com.vashchenko.education.t1.task_3.service.JwtService;
import com.vashchenko.education.t1.task_3.service.UserService;
import com.vashchenko.education.t1.task_3.web.dto.request.LoginRequestDto;
import com.vashchenko.education.t1.task_3.web.dto.request.RefreshTokenPairRequestDto;
import com.vashchenko.education.t1.task_3.web.dto.request.RegistrationRequestDto;
import com.vashchenko.education.t1.task_3.web.dto.response.TokenPairResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    private final JwtService jwtService;
    private final UserService userService;
    private final static String BEARER_PREFIX ="Bearer ";

    @PostMapping("/login")
    public TokenPairResponseDto login(@RequestBody LoginRequestDto requestDto){
        return jwtService.generateTokenPair(requestDto);
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody RegistrationRequestDto requestDto){
        userService.registration(requestDto);
        return new ResponseEntity<>("User was successful created",HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    public TokenPairResponseDto refreshTokens(@RequestBody RefreshTokenPairRequestDto requestDto){
        return jwtService.refreshTokenPair(requestDto);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateLevelToAdmin(){
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.updateLevelToAdmin(principal);
        return new ResponseEntity<>("Role of user \""+principal.getUsername()+"\" was updated to level Admin",HttpStatus.ACCEPTED);
    }
}
