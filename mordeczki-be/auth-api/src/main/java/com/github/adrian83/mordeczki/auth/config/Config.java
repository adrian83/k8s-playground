package com.github.adrian83.mordeczki.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.github.adrian83.mordeczki.auth.service.AuthTokenService;

@Configuration
public class Config {

    @Bean
    public AuthTokenService createTokenService() {
        return new AuthTokenService();
    }

    @Bean
    public BCryptPasswordEncoder createBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
