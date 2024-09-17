package com.github.adrian83.mordeczki.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.adrian83.mordeczki.auth.common.PasswordEncoder;
import com.github.adrian83.mordeczki.auth.exception.InvalidUsernameOrPasswordException;
import com.github.adrian83.mordeczki.auth.model.command.ChangePasswordCommand;
import com.github.adrian83.mordeczki.auth.model.command.CreateTokenCommand;
import com.github.adrian83.mordeczki.auth.model.command.LoginCommand;
import com.github.adrian83.mordeczki.auth.model.entity.Account;
import com.github.adrian83.mordeczki.auth.model.entity.Role;
import com.github.adrian83.mordeczki.auth.model.payload.LoginPayload;
import com.github.adrian83.mordeczki.auth.model.payload.TokenType;
import com.github.adrian83.mordeczki.common.date.DateUtil;

@Service
public class AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

    private static final long AUTH_TOKEN_VALIDITY_HOURS = 12;

    private static final RuntimeException INVALID_USERNAME_OR_PASSWORD_EXCEPTION = new InvalidUsernameOrPasswordException(
            "invalid password or username");

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AuthTokenService tokenService;

    public Account changePassword(ChangePasswordCommand command) {
        LOGGER.info("Changing password for account: {}", command.email());

        Account account = accountService.getActiveAccount(command.email());

        if (!passwordEncoder.matches(command.oldPassword(), account.getPasswordHash())) {
            throw INVALID_USERNAME_OR_PASSWORD_EXCEPTION;
        }

        var encodedNewPass = passwordEncoder.encode(command.newPassword());

        var updatedAccount = new Account(
                account.getId(),
                account.getEmail(),
                encodedNewPass,
                account.isExpired(),
                account.isLocked(),
                account.isEnabled(),
                false,
                account.getRoles());

        return accountService.save(updatedAccount);
    }

    public LoginPayload loginUser(LoginCommand command) {
        LOGGER.info("Logging in user: {}", command.email());

        Account account = accountService.getActiveAccount(command.email());

        var roles = account.getRoles().stream().map(Role::getName).toList();

        var validTo = DateUtil.utcNowPlusHours(AUTH_TOKEN_VALIDITY_HOURS);

        var tokenReq = new CreateTokenCommand(account.getEmail(), roles, validTo);
        var accessToken = tokenService.createToken(tokenReq);
        var refreshToken = "todo"; // TODO

        return new LoginPayload(TokenType.BEARER.label(), accessToken, refreshToken, validTo);

    }
}
