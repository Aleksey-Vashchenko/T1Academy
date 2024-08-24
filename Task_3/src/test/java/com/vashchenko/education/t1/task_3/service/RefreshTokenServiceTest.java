package com.vashchenko.education.t1.task_3.service;

import com.vashchenko.education.t1.task_3.exception.InvalidJwtPairException;
import com.vashchenko.education.t1.task_3.model.entity.RefreshToken;
import com.vashchenko.education.t1.task_3.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.instancio.Instancio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({RefreshTokenService.class,UserService.class,BCryptPasswordEncoder.class})
@ActiveProfiles("test")
class RefreshTokenServiceTest {

    @Value("${jwt.token.refresh.lifetime}")
    Long minutes;

    @Value("${jwt.token.refresh.secret}")
    String secret;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    RefreshTokenService refreshTokenService;
    @AfterEach
    public void clearDatabase() {
        System.out.println("Cleaning up the database...");
        jdbcTemplate.execute("DELETE FROM refresh_token");
        jdbcTemplate.execute("DELETE FROM user_roles");
        jdbcTemplate.execute("DELETE FROM users_tbl");
    }

    @Test
    void generateRefreshToken() {
        String testAccessToken = "123";
        User user = Instancio.of(User.class).create();
        String token = refreshTokenService.generateRefreshToken(user,testAccessToken.hashCode());
        String refreshToken = refreshTokenService.generateRefreshToken(user,testAccessToken.hashCode());
        Claims claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))).build().parseClaimsJws(refreshToken).getBody();

        assertEquals(UUID.fromString(claims.get(JwtService.ID_CLAIM,String.class)),user.getId());
        assertTrue(claims.getExpiration().before(Date.from(LocalDateTime.now().plusMinutes(minutes).atZone(ZoneId.systemDefault()).toInstant())));
    }

    @Test
    void findByValue() {
        String testAccessToken = "123";
        User user = Instancio.of(User.class).create();
        assertThrows(InvalidJwtPairException.class,() -> {
            refreshTokenService.findByValue("not_exists_token_value");
        });
        String refreshToken = refreshTokenService.generateRefreshToken(user,testAccessToken.hashCode());
        RefreshToken entity = refreshTokenService.findByValue(refreshToken);
    }
}