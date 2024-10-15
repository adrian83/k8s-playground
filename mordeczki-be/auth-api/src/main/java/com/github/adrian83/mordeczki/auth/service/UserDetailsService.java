package com.github.adrian83.mordeczki.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.github.adrian83.mordeczki.auth.model.MUserDetails;
import com.github.adrian83.mordeczki.auth.model.entity.Role;

import reactor.core.publisher.Mono;

@Service
public class UserDetailsService implements ReactiveUserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    private AccountService accountService;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        LOGGER.info("Username: {}", username);
        return Mono.justOrEmpty(accountService.findByEmail(username)
                .map(account
                        -> new MUserDetails(
                        account.getEmail(),
                        account.getPasswordHash(),
                        account.isAccountExpired(),
                        account.isCredentialsExpired(),
                        account.isLocked(),
                        account.isEnabled(),
                        account.getRoles().stream().map(Role::getName).toList())
                )
        );
    }

}
