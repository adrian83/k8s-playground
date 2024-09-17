package com.github.adrian83.mordeczki.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.adrian83.mordeczki.auth.common.PasswordEncoder;
import com.github.adrian83.mordeczki.auth.exception.AccountNotEnabledException;
import com.github.adrian83.mordeczki.auth.exception.InvalidUsernameOrPasswordException;
import com.github.adrian83.mordeczki.auth.exception.PasswordResetRequiredException;
import com.github.adrian83.mordeczki.auth.model.entity.Account;
import com.github.adrian83.mordeczki.auth.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Account getActiveAccount(String email) {
        return accountRepository.findByEmail(email)
                .map(account -> {
                    if (account.isExpired()) {
                        throw new PasswordResetRequiredException("password reset required for user: " + account.getEmail());
                    }

                    if (!account.isEnabled()) {
                        throw new AccountNotEnabledException("account for user: " + account.getEmail() + " is not enabled");
                    }

                    return account;
                })
                .orElseThrow(() -> new InvalidUsernameOrPasswordException("invalid password or username"));

    }

    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public Account createDisabledAccount(String email, String password) {
        var encodedPass = passwordEncoder.encode(password);
        var account = Account.newDisabledAccount(email, encodedPass);
        return accountRepository.save(account);
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public Account enableAccount(Account account) {
        account.setEnabled(true);
        return accountRepository.save(account);
    }

}
