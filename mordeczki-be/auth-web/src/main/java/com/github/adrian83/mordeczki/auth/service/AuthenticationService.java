package com.github.adrian83.mordeczki.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.adrian83.mordeczki.auth.exception.InvalidUsernameOrPasswordException;
import com.github.adrian83.mordeczki.auth.exception.PasswordResetRequiredException;
import com.github.adrian83.mordeczki.auth.model.command.CreateTokenCommand;
import com.github.adrian83.mordeczki.auth.model.command.LoginCommand;
import com.github.adrian83.mordeczki.auth.model.command.ChangePasswordCommand;
import com.github.adrian83.mordeczki.auth.model.entity.Account;
import com.github.adrian83.mordeczki.auth.model.entity.Role;
import com.github.adrian83.mordeczki.auth.repository.AccountRepository;

@Service
public class AuthenticationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

	@Autowired
	private PasswordService passwordService;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private TokenService tokenService;

	public boolean changePassword(ChangePasswordCommand command) {
		LOGGER.info("Changing password for account: {}", command.email());

		Account account = accountRepository.findById(command.email())
				.orElseThrow(() -> new InvalidUsernameOrPasswordException("invalid password or username"));

		if (!passwordService.matches(command.oldPassword(), account.getPasswordHash())) {
			throw new InvalidUsernameOrPasswordException("invalid password or username");
		}

		var encodedNewPass = passwordService.encode(command.newPassword());

		var updatedAccount = new Account(account.getEmail(), encodedNewPass, account.isExpired(), account.isLocked(),
				account.isEnabled(), false, account.getRoles());

		accountRepository.save(updatedAccount);

		return true;
	}

	public String loginUser(LoginCommand command) {
		LOGGER.info("Logging in user: {}", command.email());

		Account account = accountRepository.findById(command.email())
				.orElseThrow(() -> new InvalidUsernameOrPasswordException("invalid password or username"));

		if (account.isCredentialsExpired()) {
			throw new PasswordResetRequiredException("password reset required for user: " + account.getEmail());
		}

		var roles = account.getRoles().stream().map(Role::getName).toList();
		var tokenReq = new CreateTokenCommand(account.getEmail(), roles);
		return tokenService.createToken(tokenReq);
	}
}
