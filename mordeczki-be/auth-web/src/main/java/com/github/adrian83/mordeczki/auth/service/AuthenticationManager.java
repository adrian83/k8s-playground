package com.github.adrian83.mordeczki.auth.service;

import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Service
public class AuthenticationManager implements ReactiveAuthenticationManager, ServerSecurityContextRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationManager.class);

    @Autowired
    private TokenService tokenService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
	LOGGER.info("authenticate: {}", authentication.getPrincipal());
	return Mono.just(authentication);
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
	return Mono.justOrEmpty(exchange).map(ServerWebExchange::getRequest).flatMap(this::authTokenFromRequest)
		.map(this::tokenToAuth).flatMap(this::authenticate).map(SecurityContextImpl::new);
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
	return Mono.empty();
    }

    private UsernamePasswordAuthenticationToken tokenToAuth(String token) {
	LOGGER.info("token: {}", token);
	var data = tokenService.decodeToken(token);
	var roles = data.roles().stream().map(this::toGrantedAuthority).collect(Collectors.toList());
	return new UsernamePasswordAuthenticationToken(data.email(), data.email(), roles);
    }

    private GrantedAuthority toGrantedAuthority(String role) {
	return () -> role;
    }

    private Mono<String> authTokenFromRequest(ServerHttpRequest request) {
	var authToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
	return Mono.justOrEmpty(authToken);
    }
}
