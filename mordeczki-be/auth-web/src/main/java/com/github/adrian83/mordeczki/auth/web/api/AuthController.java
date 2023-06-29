package com.github.adrian83.mordeczki.auth.web.api;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import java.util.concurrent.CompletionStage;

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
import com.github.adrian83.mordeczki.auth.model.command.ChangePasswordCommand;
import com.github.adrian83.mordeczki.auth.service.AuthenticationService;
import com.github.adrian83.mordeczki.auth.service.RegistrationService;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private RegistrationService registrationService;

    @PostMapping(path = SecurityConfig.REGISTER, consumes = APPLICATION_JSON_VALUE)
    public CompletionStage<ResponseEntity<Void>> register(@Valid @RequestBody RegisterCommand command) {
	return registrationService.registerAccount(command).thenApply(v -> ResponseEntity.ok().build());
    }

    @PostMapping(path = SecurityConfig.LOGIN, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> login(@Valid @RequestBody LoginCommand command) {
	var token = authenticationService.loginUser(command);
	return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).build();
    }

    @PostMapping(path = SecurityConfig.CHANGE_PASS, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordCommand command) {
	authenticationService.changePassword(command);
	return ResponseEntity.ok().build();
    }
}
