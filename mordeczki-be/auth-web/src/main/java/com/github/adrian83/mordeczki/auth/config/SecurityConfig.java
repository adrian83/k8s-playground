package com.github.adrian83.mordeczki.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import com.github.adrian83.mordeczki.auth.service.AuthService;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	public static final String REGISTER = "/auth/register";
	public static final String LOGIN = "/auth/login";
	public static final String RESET_PASS = "/auth/resetpass";

	public static final String EVERYWHERE = "/**";
	public static final String HEALTH = "/health";

	@Bean
	public SecurityWebFilterChain securitygWebFilterChain(final ServerHttpSecurity http,
			final AuthService authService) {
		return http.httpBasic()
				.disable()
				.formLogin()
				.disable()
				.csrf()
				.disable()
				.authorizeExchange()
				.pathMatchers(HttpMethod.OPTIONS, EVERYWHERE)
				.permitAll()
				.pathMatchers(REGISTER, LOGIN, RESET_PASS, HEALTH)
				.permitAll()
				.pathMatchers(EVERYWHERE)
				.authenticated()
				.anyExchange()
				.authenticated()
				.and()
				.authenticationManager(authService)
				.securityContextRepository(authService)
				.build();
	}

	@Bean
	public BCryptPasswordEncoder createBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
