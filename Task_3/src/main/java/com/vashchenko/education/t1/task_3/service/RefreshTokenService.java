package com.vashchenko.education.t1.task_3.service;

import com.vashchenko.education.t1.task_3.exception.InvalidJwtPairException;
import com.vashchenko.education.t1.task_3.model.entity.RefreshToken;
import com.vashchenko.education.t1.task_3.model.entity.User;
import com.vashchenko.education.t1.task_3.repository.JpaRefreshTokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class RefreshTokenService {
    private final Key jwtKey;
    private final Long tokenLifetime;

    private final JpaRefreshTokenRepository tokenRepository;

    public RefreshTokenService(@Value("${jwt.token.refresh.secret}") String jwtRefreshSecret,
                      @Value("${jwt.token.refresh.lifetime}") Long tokenLifetime,
                               JpaRefreshTokenRepository refreshTokenRepository){
        this.tokenLifetime=tokenLifetime;
        this.jwtKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
        this.tokenRepository=refreshTokenRepository;
    }

    public String generateRefreshToken(User user, Integer accessHash) {
        final Instant accessExpirationInstant = LocalDateTime.now().plusMinutes(tokenLifetime).atZone(ZoneId.systemDefault()).toInstant();
        String refreshToken = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(Date.from(accessExpirationInstant))
                .signWith(jwtKey)
                .compact();
        RefreshToken token = new RefreshToken();
        token.setUserId(user.getId());
        token.setTokenValue(refreshToken);
        token.setExpiredAt(Date.from(accessExpirationInstant));
        token.setCreatedAt(Date.from(Instant.now()));
        token.setAccessHash(accessHash);
        tokenRepository.save(token);
        return refreshToken;
    }

    public RefreshToken findByValue(String tokenValue){
        return tokenRepository.findByTokenValue(tokenValue).orElseThrow(InvalidJwtPairException::new);
    }
}
