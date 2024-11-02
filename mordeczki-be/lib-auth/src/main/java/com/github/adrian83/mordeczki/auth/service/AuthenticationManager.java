package com.github.adrian83.mordeczki.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;


public class AuthenticationManager implements ReactiveAuthenticationManager, ServerSecurityContextRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationManager.class);

    private final AuthTokenDecoder authTokenDecoder;

    public AuthenticationManager(AuthTokenDecoder authTokenDecoder) {
        this.authTokenDecoder = authTokenDecoder;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        LOGGER.info("authenticate: {}", authentication.getPrincipal());
        return Mono.just(authentication);
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        LOGGER.info("load security context: {}", exchange.getRequest());
        return Mono.justOrEmpty(exchange)
            .map(ServerWebExchange::getRequest)
            .flatMap(this::authTokenFromRequest)
            .flatMap(this::tokenToAuth)
            .flatMap(this::authenticate)
            .map(SecurityContextImpl::new);
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    private Mono<UsernamePasswordAuthenticationToken> tokenToAuth(String token) {
        LOGGER.info("token: {}", token);
        return authTokenDecoder.decodeAuthToken(token)
            .map(details -> new UsernamePasswordAuthenticationToken(details, token, details.getAuthorities()));

    }

    private Mono<String> authTokenFromRequest(ServerHttpRequest request) {
        var authToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        LOGGER.info("headers: {}", request.getHeaders());
        LOGGER.info("auth: {}", authToken);
        return Mono.justOrEmpty(authToken);
    }
}
