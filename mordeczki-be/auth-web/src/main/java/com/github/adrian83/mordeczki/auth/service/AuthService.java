package com.github.adrian83.mordeczki.auth.service;

import java.util.Collections;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import com.github.adrian83.mordeczki.auth.exception.InvalidUsernameOrPasswordException;
import com.github.adrian83.mordeczki.auth.exception.PasswordResetRequiredException;
import com.github.adrian83.mordeczki.auth.model.command.CreateTokenCommand;
import com.github.adrian83.mordeczki.auth.model.command.LoginCommand;
import com.github.adrian83.mordeczki.auth.model.command.RegisterCommand;
import com.github.adrian83.mordeczki.auth.model.command.ResetPasswordCommand;
import com.github.adrian83.mordeczki.auth.model.entity.Role;
import com.github.adrian83.mordeczki.auth.model.entity.Account;
import com.github.adrian83.mordeczki.auth.repository.AccountRepository;

import reactor.core.publisher.Mono;

@Service
public class AuthService implements ReactiveAuthenticationManager, ServerSecurityContextRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

	@Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired private AccountRepository accountRepository;
	@Autowired private TokenService tokenService;

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;
	
    @Value(value = "${topic.registeredUser}")
    private String registeredUserTopic;

    
	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		LOGGER.info("authenticate: {}", authentication.getPrincipal());
		return Mono.just(authentication);
	}

	@Override
	public Mono<SecurityContext> load(ServerWebExchange exchange) {
		return Mono.justOrEmpty(exchange)
				.map(ServerWebExchange::getRequest)
				.flatMap(this::authTokenFromRequest)
				.map(this::tokenToAuth)
				.flatMap(this::authenticate)
				.map(SecurityContextImpl::new);
	}

	@Override
	public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
		return Mono.empty();
	}

	public Account registerAccount(RegisterCommand command) {
		var encodedPass = bCryptPasswordEncoder.encode(command.password());
		var account = new Account(
				command.email(), 
				encodedPass, 
				false, 
				false, 
				true, 
				false, 
				Collections.emptySet()
			);

		var saved = accountRepository.save(account);
		
		kafkaTemplate.send(registeredUserTopic, saved);
		
		return saved;
	}

	public void resetPassword(ResetPasswordCommand command) {
		Account account = accountRepository.findById(command.email())
				.orElseThrow(() -> new InvalidUsernameOrPasswordException("invalid password or username"));

		var encodedOldPass = bCryptPasswordEncoder.encode(command.oldPassword());
		if (!account.getPasswordHash().equals(encodedOldPass)) {
			throw new InvalidUsernameOrPasswordException("invalid password or username");
		}

		var encodedNewPass = bCryptPasswordEncoder.encode(command.newPassword());

		account.setPasswordHash(encodedNewPass);
		account.setCredentialsExpired(false);

		accountRepository.save(account);
	}

	public String loginUser(LoginCommand command) {
		Account account = accountRepository.findById(command.email())
				.orElseThrow(() -> new InvalidUsernameOrPasswordException("invalid password or username"));

		if (account.isCredentialsExpired()) {
			throw new PasswordResetRequiredException("password reset required for user: " + account.getEmail());
		}

		var roles = account.getRoles().stream().map(Role::getName).collect(Collectors.toList());
		var tokenReq = new CreateTokenCommand(account.getEmail(), roles);
		return tokenService.createToken(tokenReq);
	}

	private UsernamePasswordAuthenticationToken tokenToAuth(String token) {
		LOGGER.info("token: {}", token);
		var data = tokenService.decodeToken(token);
		var roles = data.roles().stream().map(this::toGrantedAuthority).collect(Collectors.toList());

		return new UsernamePasswordAuthenticationToken(data.email(), data.email(), roles);
	}

	private GrantedAuthority toGrantedAuthority(String role) {
		return () -> role;
	}

	private Mono<String> authTokenFromRequest(ServerHttpRequest request) {
		var authToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		return Mono.justOrEmpty(authToken);
	}
}
