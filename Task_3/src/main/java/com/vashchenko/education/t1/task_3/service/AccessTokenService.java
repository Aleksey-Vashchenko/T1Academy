package com.vashchenko.education.t1.task_3.service;

import com.vashchenko.education.t1.task_3.model.entity.Role;
import com.vashchenko.education.t1.task_3.model.entity.User;
import com.vashchenko.education.t1.task_3.security.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class AccessTokenService {
    private static final String ROLES_CLAIM = "roles";
    private static final String ID_CLAIM = "id";
    private final Key jwtKey;
    private final Duration tokenLifetime;

    public AccessTokenService(@Value("${jwt.token.access.secret}") String jwtAccessSecret,
                              @Value("${jwt.token.access.lifetime}") Duration tokenLifetime){
        this.tokenLifetime=tokenLifetime;
        this.jwtKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
    }

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(new Date().getTime()+tokenLifetime.toMillis()))
                .signWith(jwtKey)
                .claim(ROLES_CLAIM, user.getRoles())
                .claim(ID_CLAIM,user.getId())
                .compact();
    }

    public Authentication toAuthentication(String accessToken) throws JwtException,IllegalArgumentException {
        Claims tokenBody = Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(accessToken).getBody();
        String subject =tokenBody.getSubject();
        UUID id = tokenBody.get(ID_CLAIM,UUID.class);
        Set<Role> roles = (Set<Role>) tokenBody.get(ROLES_CLAIM);
        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setId(id);
        userPrincipal.setUsername(subject);
        userPrincipal.setRoles(roles);
        return new UsernamePasswordAuthenticationToken(userPrincipal,accessToken,roles);
    }

    public UUID getClaimsWithoutExpiration(String accessToken){
        try {
            Claims claimsJws = Jwts.parserBuilder()
                    .setSigningKey(jwtKey)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
            return claimsJws.get(ID_CLAIM,UUID.class);
        }
        catch (ExpiredJwtException e){
            return e.getClaims().get(ID_CLAIM,UUID.class);
        }
    }
}
