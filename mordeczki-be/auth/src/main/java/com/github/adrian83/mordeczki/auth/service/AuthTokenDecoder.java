package com.github.adrian83.mordeczki.auth.service;

import org.springframework.security.core.userdetails.UserDetails;

import reactor.core.publisher.Mono;

public interface AuthTokenDecoder {

    Mono<UserDetails> decodeAuthToken(String authToken);
}
