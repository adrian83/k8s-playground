package com.github.adrian83.mordeczki.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.github.adrian83.mordeczki.auth.service.TokenService;

@Configuration
public class Config {

    @Bean
    public TokenService createTokenService() {
	return new TokenService();
    }

    @Bean
    public BCryptPasswordEncoder createBCryptPasswordEncoder() {
	return new BCryptPasswordEncoder();
    }
}
