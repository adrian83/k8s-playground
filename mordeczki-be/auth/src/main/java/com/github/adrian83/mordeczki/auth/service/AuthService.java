package com.github.adrian83.mordeczki.auth.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import com.github.adrian83.mordeczki.auth.model.command.LoginCommand;
import com.github.adrian83.mordeczki.auth.model.command.RegisterCommand;
import com.github.adrian83.mordeczki.auth.model.command.TokenRequest;
import com.github.adrian83.mordeczki.auth.model.entity.Role;
import com.github.adrian83.mordeczki.auth.model.entity.User;
import com.github.adrian83.mordeczki.auth.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class AuthService implements ReactiveAuthenticationManager, ServerSecurityContextRepository {

  @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
  @Autowired private UserRepository userRepository;
  @Autowired private TokenService tokenService;

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    log.info("authen: {}", authentication.getPrincipal());
    return Mono.just(authentication);
  }

  @Override
  public Mono<SecurityContext> load(ServerWebExchange exchange) {
    return Mono.justOrEmpty(exchange)
        .map(ServerWebExchange::getRequest)
        .flatMap(this::authTokenFromRequest)
        .map(this::tokenToAuth)
        .flatMap(this::authenticate)
        .map(SecurityContextImpl::new);
  }

  @Override
  public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
    return Mono.empty();
  }

  public User registerUser(RegisterCommand command) {
    var encodedPass = bCryptPasswordEncoder.encode(command.getPassword());
    var user = User.builder().email(command.getEmail()).passwordHash(encodedPass).build();
    return userRepository.save(user);
  }

  public String loginUser(LoginCommand command) {
    User user =
        userRepository
            .findById(command.getEmail())
            .orElseThrow(() -> new RuntimeException("user not found"));

    var roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
    var tokenReq = TokenRequest.builder().email(user.getEmail()).roles(roles).build();
    return tokenService.createToken(tokenReq);
  }

  private UsernamePasswordAuthenticationToken tokenToAuth(String token) {
    log.info("token: {}", token);
    var data = tokenService.decodeToken(token);
    var roles = data.getRoles().stream().map(this::toGrantedAuthority).collect(Collectors.toList());

    return new UsernamePasswordAuthenticationToken(data.getEmail(), data.getEmail(), roles);
  }

  private GrantedAuthority toGrantedAuthority(String role) {
    return () -> role;
  }

  private Mono<String> authTokenFromRequest(ServerHttpRequest request) {
    var authToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    return Mono.justOrEmpty(authToken);
  }
}
