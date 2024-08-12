package com.github.adrian83.mordeczki.auth.web.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.adrian83.mordeczki.auth.config.SecurityConfig;
import com.github.adrian83.mordeczki.auth.model.command.ActivateCommand;
import com.github.adrian83.mordeczki.auth.model.command.ChangePasswordCommand;
import com.github.adrian83.mordeczki.auth.model.command.LoginCommand;
import com.github.adrian83.mordeczki.auth.model.command.RegisterCommand;
import com.github.adrian83.mordeczki.auth.model.command.RequestResetPasswordCommand;
import com.github.adrian83.mordeczki.auth.model.command.ResetPasswordCommand;
import com.github.adrian83.mordeczki.auth.service.AuthenticationService;
import com.github.adrian83.mordeczki.auth.service.RegistrationService;
import com.github.adrian83.mordeczki.auth.service.ResetPasswordService;

import reactor.core.publisher.Mono;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private ResetPasswordService resetPasswordService;

    @PostMapping(path = SecurityConfig.REGISTER, consumes = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> register(@Valid @RequestBody Mono<RegisterCommand> mCommand) {
        return mCommand.map(command -> registrationService.registerAccount(command))
                .map(v -> ResponseEntity.ok().build())
                .onErrorResume(t -> Mono.just(AuthExceptionHandler.handleError(t)));
    }

    @GetMapping(path = SecurityConfig.ACTIVATE, consumes = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> activate(@RequestParam("token") String token) {
        return Mono.just(registrationService.activateAccount(new ActivateCommand(token)))
                .map(v -> ResponseEntity.ok().build())
                .onErrorResume(t -> Mono.just(AuthExceptionHandler.handleError(t)));
    }

    @PostMapping(path = SecurityConfig.LOGIN, consumes = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> login(@Valid @RequestBody Mono<LoginCommand> mCommand) {
        return mCommand.map(command -> authenticationService.loginUser(command))
                .map(payload -> ResponseEntity.ok().body((Object) payload))
                .onErrorResume(t -> Mono.just(AuthExceptionHandler.handleError(t)));
    }

    @PostMapping(path = SecurityConfig.CHANGE_PASS, consumes = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> changePassword(@Valid @RequestBody Mono<ChangePasswordCommand> mCommand) {
        return mCommand.map(command -> authenticationService.changePassword(command))
                .map(o -> ResponseEntity.ok().build())
                .onErrorResume(t -> Mono.just(AuthExceptionHandler.handleError(t)));
    }

    @PostMapping(path = SecurityConfig.REQ_RESET_PASS, consumes = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> requestPasswordReset(
            @Valid @RequestBody Mono<RequestResetPasswordCommand> mCommand) {
        return mCommand
                .flatMap(command -> Mono.fromCompletionStage(resetPasswordService.handleResetPasswordRequest(command)))
                .map(o -> ResponseEntity.ok().build())
                .onErrorResume(t -> Mono.just(AuthExceptionHandler.handleError(t)));
    }

    @PostMapping(path = SecurityConfig.RESET_PASS, consumes = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> resetPassword(@Valid @RequestBody Mono<ResetPasswordCommand> mCommand) {
        return mCommand.map(command -> resetPasswordService.changePassword(command))
                .map(o -> ResponseEntity.ok().build())
                .onErrorResume(t -> Mono.just(AuthExceptionHandler.handleError(t)));
    }

}
