package com.github.adrian83.mordeczki.auth.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.adrian83.mordeczki.auth.config.SecurityConfig;
import com.github.adrian83.mordeczki.auth.model.command.LoginCommand;
import com.github.adrian83.mordeczki.auth.model.command.RegisterCommand;
import com.github.adrian83.mordeczki.auth.model.entity.User;
import com.github.adrian83.mordeczki.auth.service.AuthService;

import reactor.core.publisher.Mono;

@RestController
public class AuthController {

  @Autowired private AuthService authService;

  @PostMapping(path = SecurityConfig.REGISTER, consumes = "application/json")
  public Mono<User> register(@Valid @RequestBody RegisterCommand command) {
    var user = authService.registerUser(command);
    return Mono.just(user);
  }

  @PostMapping(path = SecurityConfig.LOGIN, consumes = "application/json")
  public ResponseEntity<Void> login(@Valid @RequestBody LoginCommand command) {
    var token = authService.loginUser(command);
    return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).build();
  }
}
