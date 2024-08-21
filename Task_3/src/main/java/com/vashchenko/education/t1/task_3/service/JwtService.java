package com.vashchenko.education.t1.task_3.service;

import com.vashchenko.education.t1.task_3.exception.InvalidJwtPairException;
import com.vashchenko.education.t1.task_3.exception.WrongLoginOrPasswordException;
import com.vashchenko.education.t1.task_3.model.entity.RefreshToken;
import com.vashchenko.education.t1.task_3.model.entity.User;
import com.vashchenko.education.t1.task_3.security.PasswordValidator;
import com.vashchenko.education.t1.task_3.web.dto.request.LoginRequestDto;
import com.vashchenko.education.t1.task_3.web.dto.request.RefreshTokenPairRequestDto;
import com.vashchenko.education.t1.task_3.web.dto.response.TokenPairResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final PasswordValidator passwordValidator;

    public Authentication authenticate(String accessToken) throws JwtException,IllegalArgumentException{
        return accessTokenService.toAuthentication(accessToken);
    }

    public TokenPairResponseDto generateTokenPair(LoginRequestDto request) throws WrongLoginOrPasswordException {
        User user = userService.findByEmail(request.email());
        passwordValidator.validatePassword(request.password(), user.getPasswordHash());
        String accessToken = accessTokenService.generateAccessToken(user);
        String refreshToken = refreshTokenService.generateRefreshToken(user,accessToken.hashCode());
        return new TokenPairResponseDto(accessToken,refreshToken);
    }
    public TokenPairResponseDto refreshTokenPair(RefreshTokenPairRequestDto request, String accessToken) throws JwtException,IllegalArgumentException {
        String refreshTokenValue = request.refreshToken();
        RefreshToken refreshToken = refreshTokenService.findByValue(refreshTokenValue);
        if(!refreshToken.getAccessHash().equals(accessToken.hashCode())){
            throw new InvalidJwtPairException();
        }
        UUID uuid = accessTokenService.getClaimsWithoutExpiration(accessToken);
        User user = userService.findById(uuid);
        String newAccessToken = accessTokenService.generateAccessToken(user);
        String newRefreshToken = refreshTokenService.generateRefreshToken(user,newAccessToken.hashCode());
        return new TokenPairResponseDto(newAccessToken,newRefreshToken);
    }

}
