package com.github.adrian83.mordeczki.auth.common;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TokenGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenGenerator.class);

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

}
