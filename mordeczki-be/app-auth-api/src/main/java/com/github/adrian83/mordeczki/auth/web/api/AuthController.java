package com.github.adrian83.mordeczki.auth.web.api;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.adrian83.mordeczki.auth.config.SecurityConfig;
import com.github.adrian83.mordeczki.auth.model.command.ActivateCommand;
import com.github.adrian83.mordeczki.auth.model.command.BothPasswordsCommand;
import com.github.adrian83.mordeczki.auth.model.command.ChangePasswordCommand;
import com.github.adrian83.mordeczki.auth.model.command.LoginCommand;
import com.github.adrian83.mordeczki.auth.model.command.RefreshTokensCommand;
import com.github.adrian83.mordeczki.auth.model.command.RegisterCommand;
import com.github.adrian83.mordeczki.auth.model.command.RequestResetPasswordCommand;
import com.github.adrian83.mordeczki.auth.model.command.ResetPasswordCommand;
import com.github.adrian83.mordeczki.auth.service.AccountService;
import com.github.adrian83.mordeczki.auth.service.AuthenticationService;
import com.github.adrian83.mordeczki.auth.service.PasswordService;
import com.github.adrian83.mordeczki.auth.service.RegistrationService;

import reactor.core.publisher.Mono;

@RestController
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private PasswordService resetPasswordService;
    @Autowired
    private AccountService accountService;

    @PostMapping(path = SecurityConfig.REGISTER, consumes = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> register(@Valid @RequestBody Mono<RegisterCommand> mCommand) {
        return mCommand.map(command -> registrationService.registerAccount(command))
                .map(v -> ResponseEntity.ok().build())
                .onErrorResume(t -> Mono.just(AuthExceptionHandler.handleError(t)));
    }

    @GetMapping(path = SecurityConfig.ACTIVATE)
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
    public Mono<ResponseEntity<Object>> changePassword(
            @AuthenticationPrincipal Mono<UserDetails> mUserDetails,
            @Valid @RequestBody Mono<BothPasswordsCommand> mCommand
    ) {
        return mUserDetails.flatMap(userDetails -> mCommand.map(passwords -> new ChangePasswordCommand(userDetails.getUsername(), passwords.oldPassword(), passwords.newPassword())))
                .map(command -> authenticationService.changePassword(command))
                .map(o -> ResponseEntity.ok().build())
                .onErrorResume(t -> Mono.just(AuthExceptionHandler.handleError(t)));
    }

    @PostMapping(path = SecurityConfig.REQ_RESET_PASS, consumes = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> requestPasswordReset(@Valid @RequestBody Mono<RequestResetPasswordCommand> mCommand) {
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

    // TODO only admin
    @GetMapping(path = SecurityConfig.LOCK)
    public Mono<ResponseEntity<Object>> lockAccount(
            @AuthenticationPrincipal Mono<UserDetails> mUserDetails,
            @RequestParam("email") String email
    ) {
        return Mono.just(accountService.lockAccount(email))
                .map(o -> ResponseEntity.ok().build())
                .onErrorResume(t -> Mono.just(AuthExceptionHandler.handleError(t)));
    }

    // TODO only admin
    @GetMapping(path = SecurityConfig.UNLOCK)
    public Mono<ResponseEntity<Object>> unlockAccount(
            @AuthenticationPrincipal Mono<UserDetails> mUserDetails,
            @RequestParam("email") String email
    ) {
        return Mono.just(accountService.unlockAccount(email))
                .map(o -> ResponseEntity.ok().build())
                .onErrorResume(t -> Mono.just(AuthExceptionHandler.handleError(t)));
    }

    @PostMapping(path = SecurityConfig.REFRESH, consumes = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> refresh(@Valid @RequestBody Mono<RefreshTokensCommand> mCommand) {
        return mCommand.map(command -> authenticationService.refreshTokens(command))
                .map(payload -> ResponseEntity.ok().body((Object) payload))
                .onErrorResume(t -> Mono.just(AuthExceptionHandler.handleError(t)));
    }

  
    @GetMapping(path = SecurityConfig.USER_DATA)
    public Mono<ResponseEntity<Object>> getUserData(@AuthenticationPrincipal Mono<UserDetails> mUserDetails) {
        return mUserDetails.map(userDetails -> ResponseEntity.ok().body((Object) userDetails))
                .onErrorResume(t -> Mono.just(AuthExceptionHandler.handleError(t)));
    }
}
