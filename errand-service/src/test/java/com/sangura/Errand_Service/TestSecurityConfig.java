package com.sangura.Errand_Service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class TestSecurityConfig {
    @Bean
    JwtDecoder jwtDecoder() {
        return mock(JwtDecoder.class); // prevents Spring from wiring the real thing
    }
}

