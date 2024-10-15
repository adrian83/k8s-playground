package com.github.adrian83.mordeczki.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class LocalAuthTokenDecoder implements AuthTokenDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalAuthTokenDecoder.class);

    @Autowired
    private AuthTokenService authTokenService;

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    public Mono<UserDetails> decodeAuthToken(String authToken) {
        var data = authTokenService.decodeAuthToken(authToken);
        var email = data.email();
        return userDetailsService.findByUsername(email);
    }
    
}