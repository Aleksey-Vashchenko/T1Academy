package com.vashchenko.education.t1.task_3.web.controllers;

import com.vashchenko.education.t1.task_3.exception.InvalidJwtPairException;
import com.vashchenko.education.t1.task_3.exception.UserAlreadyExistsException;
import com.vashchenko.education.t1.task_3.exception.WrongLoginOrPasswordException;
import com.vashchenko.education.t1.task_3.security.UserPrincipal;
import com.vashchenko.education.t1.task_3.service.JwtService;
import com.vashchenko.education.t1.task_3.service.UserService;
import com.vashchenko.education.t1.task_3.web.dto.request.LoginRequestDto;
import com.vashchenko.education.t1.task_3.web.dto.request.RefreshTokenPairRequestDto;
import com.vashchenko.education.t1.task_3.web.dto.request.RegistrationRequestDto;
import com.vashchenko.education.t1.task_3.web.dto.response.TokenPairResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(
        name = "AuthenticationController",
        description = "Controller responsible for actions with authorization and tokens"
)

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    private final JwtService jwtService;
    private final UserService userService;

    @Operation(
            summary = "Login in application",
            description = "Used to get pair of token for existed user"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful login"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Wrong login or password"
            )
    })
    @PostMapping("/login")
    public TokenPairResponseDto login(@RequestBody LoginRequestDto requestDto) throws WrongLoginOrPasswordException {
        return jwtService.generateTokenPair(requestDto);
    }

    @Operation(
            summary = "Registration",
            description = "Used to create a new user"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Successful registration"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad credentials for registration"
            )
    })
    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody RegistrationRequestDto requestDto) throws UserAlreadyExistsException {
        userService.registration(requestDto);
        return new ResponseEntity<>("User was successful created",HttpStatus.CREATED);
    }

    @Operation(
            summary = "Refresh token pair",
            description = "Used to get a new pair og tokens"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful refreshing"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Error while trying to update tokens"
            )
    })
    @PostMapping("/refresh")
    public TokenPairResponseDto refreshTokens(@RequestBody RefreshTokenPairRequestDto requestDto) throws InvalidJwtPairException {
        return jwtService.refreshTokenPair(requestDto);
    }

    @Operation(
            summary = "Update user role to Admin",
            description = "Added to user role of Admin"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "202",
                    description = "Successful updating"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User is not found in database"
            )
    })
    @PostMapping("/update")
    public ResponseEntity<String> updateLevelToAdmin(){
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.updateLevelToAdmin(principal);
        return new ResponseEntity<>("Role of user \""+principal.getUsername()+"\" was updated to level Admin",HttpStatus.ACCEPTED);
    }
}
