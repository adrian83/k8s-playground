package com.github.adrian83.mordeczki.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.github.adrian83.mordeczki.auth.service.AuthenticationManager;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    public static final String REGISTER = "/register";
    public static final String ACTIVATE = "/activate";
    public static final String LOGIN = "/login";
    public static final String CHANGE_PASS = "/changepass";
    public static final String REQ_RESET_PASS = "/reqresetpass";
    public static final String RESET_PASS = "/resetpass";

    public static final String VIEW = "/view/**";
    public static final String EVERYWHERE = "/**";
    public static final String HEALTH = "/health";
    public static final String FAVICON = "/favicon.ico";

    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(final ServerHttpSecurity http,
	    final AuthenticationManager authService) {
	return http.httpBasic().disable()
		.formLogin().disable()
		.csrf().disable()
		.authorizeExchange()
		.pathMatchers(HttpMethod.OPTIONS, EVERYWHERE).permitAll()
		.pathMatchers(HttpMethod.GET, ACTIVATE, VIEW, FAVICON).permitAll()
		.pathMatchers(HttpMethod.POST, REGISTER, LOGIN, CHANGE_PASS).permitAll()
		.pathMatchers(HttpMethod.GET, HEALTH).permitAll()
		.pathMatchers(EVERYWHERE).authenticated()
		.anyExchange().authenticated()
		.and()
		.authenticationManager(authService)
		.securityContextRepository(authService)
		.build();
    }

}
