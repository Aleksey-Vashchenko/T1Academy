package com.vashchenko.education.t1.task_3.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;

@Import({AccessTokenService.class})
class AccessTokenServiceTest {
    AccessTokenService accessTokenService;

    @Value("${jwt.token.access.lifetime}")
    Long minutes;

    @Value("${jwt.token.access.secret}")
    String secret;

    @Test
    void generateAccessToken() {

    }

    @Test
    void toAuthentication() {
    }

    @Test
    void getClaimsWithoutExpiration() {
    }
}