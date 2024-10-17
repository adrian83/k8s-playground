package com.github.adrian83.mordeczki.mailer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class Config {

    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);

    @Bean
    ObjectMapper createObjectMapper() {
        return new ObjectMapper();
    }

}
