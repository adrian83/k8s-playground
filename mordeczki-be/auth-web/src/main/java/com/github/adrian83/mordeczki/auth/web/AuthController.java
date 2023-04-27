package com.github.adrian83.mordeczki.auth.web;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.github.adrian83.mordeczki.auth.config.SecurityConfig;
import com.github.adrian83.mordeczki.auth.model.command.LoginCommand;
import com.github.adrian83.mordeczki.auth.model.command.RegisterCommand;
import com.github.adrian83.mordeczki.auth.model.command.ResetPasswordCommand;
import com.github.adrian83.mordeczki.auth.service.AuthService;


@RestController
public class AuthController {

  @Autowired private AuthService authService;

  @PostMapping(path = SecurityConfig.REGISTER, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> register(@Valid @RequestBody RegisterCommand command) {
    authService.registerAccount(command);
    return ResponseEntity.ok().build();
  }

  @PostMapping(path = SecurityConfig.LOGIN, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> login(@Valid @RequestBody LoginCommand command) {
    var token = authService.loginUser(command);
    return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).build();
  }
  
  @PostMapping(path = SecurityConfig.RESET_PASS, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordCommand command) {
    authService.resetPassword(command);
    return ResponseEntity.ok().build();
  }
}
