package com.github.adrian83.mordeczki.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.github.adrian83.mordeczki.auth.service.AuthTokenDecoder;
import com.github.adrian83.mordeczki.auth.service.AuthenticationManager;

@Configuration
public class Config {

    @Bean
    public AuthenticationManager createAuthenticationManager(@Autowired AuthTokenDecoder authTokenDecoder) {
        return new AuthenticationManager(authTokenDecoder);
    }

    @Bean
    public BCryptPasswordEncoder createBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
