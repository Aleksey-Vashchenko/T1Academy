package com.vashchenko.education.t1.task_3.service;

import com.vashchenko.education.t1.task_3.exception.InvalidJwtPairException;
import com.vashchenko.education.t1.task_3.model.entity.RefreshToken;
import com.vashchenko.education.t1.task_3.model.entity.User;
import com.vashchenko.education.t1.task_3.repository.JpaRefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
public class RefreshTokenService {
    private final Key jwtKey;
    private final Duration tokenLifetime;

    private final JpaRefreshTokenRepository tokenRepository;

    public RefreshTokenService(@Value("${jwt.token.access.secret}") String jwtRefreshSecret,
                      @Value("${jwt.token.access.lifetime}") Duration tokenLifetime,
                               JpaRefreshTokenRepository refreshTokenRepository){
        this.tokenLifetime=tokenLifetime;
        this.jwtKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
        this.tokenRepository=refreshTokenRepository;
    }

    public String generateRefreshToken(User user, Integer accessHash) {
        String refreshToken = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(new Date().getTime()+tokenLifetime.toMillis()))
                .signWith(jwtKey)
                .compact();
        RefreshToken token = new RefreshToken();
        token.setUserId(user.getId());
        token.setTokenValue(refreshToken);
        token.setExpiredAt(new Date(new Date().getTime()+tokenLifetime.toMillis()));
        token.setCreatedAt(Date.from(Instant.now()));
        token.setAccessHash(accessHash);
        tokenRepository.save(token);
        return refreshToken;
    }

    public RefreshToken findByValue(String tokenValue){
        return tokenRepository.findByTokenValue(tokenValue).orElseThrow(InvalidJwtPairException::new);
    }
}
