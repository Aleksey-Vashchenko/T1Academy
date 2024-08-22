package com.vashchenko.education.t1.task_3.service;

import com.vashchenko.education.t1.task_3.model.entity.Role;
import com.vashchenko.education.t1.task_3.model.entity.User;
import com.vashchenko.education.t1.task_3.security.JwtAuthentication;
import com.vashchenko.education.t1.task_3.security.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
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
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.vashchenko.education.t1.task_3.service.JwtService.ID_CLAIM;
import static com.vashchenko.education.t1.task_3.service.JwtService.ROLES_CLAIM;

@Service
public class AccessTokenService {
    private final Key jwtKey;
    private final Long tokenLifetime;

    public AccessTokenService(@Value("${jwt.token.access.secret}") String jwtAccessSecret,
                              @Value("${jwt.token.access.lifetime}") Long tokenLifetime){
        this.tokenLifetime=tokenLifetime;
        this.jwtKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
    }

    public String generateAccessToken(User user) {
        final Instant accessExpirationInstant = LocalDateTime.now().plusMinutes(tokenLifetime).atZone(ZoneId.systemDefault()).toInstant();
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(Date.from(accessExpirationInstant))
                .signWith(jwtKey)
                .claim(ROLES_CLAIM, user.getRoles())
                .claim(ID_CLAIM,user.getId())
                .compact();
    }

    public JwtAuthentication toAuthentication(String accessToken) throws JwtException,IllegalArgumentException {
        Claims tokenBody = Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(accessToken).getBody();
        String subject =tokenBody.getSubject();
        UUID id = UUID.fromString(tokenBody.get(ID_CLAIM,String.class));
        List<String> rolesStr = tokenBody.get(ROLES_CLAIM,List.class);
        Set<Role> roles = rolesStr.stream().map(Role::valueOf).collect(Collectors.toSet());
        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setId(id);
        userPrincipal.setUsername(subject);
        userPrincipal.setRoles(roles);
        return new JwtAuthentication(userPrincipal,accessToken);
    }

    public Claims getClaimsWithoutExpiration(String accessToken){
        try {
            Claims claimsJws = Jwts.parserBuilder()
                    .setSigningKey(jwtKey)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
            return claimsJws;
        }
        catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }
}
