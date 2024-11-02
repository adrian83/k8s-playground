package com.github.adrian83.mordeczki.auth.service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.adrian83.mordeczki.auth.common.TokenGenerator;
import com.github.adrian83.mordeczki.auth.exception.EmailAlreadyInUseException;
import com.github.adrian83.mordeczki.auth.model.command.ActivateCommand;
import com.github.adrian83.mordeczki.auth.model.command.RegisterCommand;
import com.github.adrian83.mordeczki.auth.model.entity.Account;
import com.github.adrian83.mordeczki.auth.model.entity.EnableAccount;
import com.github.adrian83.mordeczki.auth.repository.EnableAccountRepository;
import com.github.adrian83.mordeczki.common.date.DateUtil;
import com.github.adrian83.mordeczki.queue.message.RegisterAccountMessage;

@Service
public class RegistrationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationService.class);

    private static final long ENABLE_ACCOUNT_TOKEN_VALIDITY_HOURS = 4;

    @Autowired
    private AccountService accountService;
    @Autowired
    private TokenGenerator tokenGenerator;
    @Autowired
    private EnableAccountRepository enableAccountRepository;
    @Autowired
    private NotificationService notificationService;

    public CompletableFuture<Account> registerAccount(RegisterCommand command) {
        var mAccount = accountService.findByEmail(command.email());
        boolean alreadyEnabled = mAccount.map(Account::isEnabled).orElse(false);

        if (alreadyEnabled) {
            return CompletableFuture.failedFuture(new EmailAlreadyInUseException("email already in use"));
        }

        var account = accountService.createDisabledAccount(command.email(), command.password());
        LOGGER.info("Account: {} persisted", account.getEmail());

        var tokenValue = tokenGenerator.generateToken();
        var enableAccount = new EnableAccount(tokenValue, account, DateUtil.utcNowPlusHours(ENABLE_ACCOUNT_TOKEN_VALIDITY_HOURS));
        enableAccountRepository.save(enableAccount);
        LOGGER.info("Token for enabling account: {} generated with value: {}", account.getEmail(), tokenValue);

        var registerAccountMessage = new RegisterAccountMessage(account.getEmail(), tokenValue);

        return notificationService.notifyAccountCreation(registerAccountMessage)
            .thenApply(msg -> { return account; });
    }

    public Optional<Account> activateAccount(ActivateCommand command) {
        return enableAccountRepository.findById(command.token())
                .map(this::ensureEnableAccountTokenIsValid)
                .map(EnableAccount::getAccount)
                .map(accountService::enableAccount);
    }

    private EnableAccount ensureEnableAccountTokenIsValid(EnableAccount enableAccount) {
        var tokenEndOfLife = enableAccount.getCreationTime().plusHours(ENABLE_ACCOUNT_TOKEN_VALIDITY_HOURS);
        if (DateUtil.isBefore(tokenEndOfLife, DateUtil.utcNow())) {
            throw new IllegalArgumentException("token invalid");
        }
        return enableAccount;
    }

}
