package com.github.adrian83.mordeczki.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
//	    return http.authorizeExchange()
//	      .anyExchange().authenticated().and().csrf().disable().
//	      .and().build();
	    
	    return http.httpBasic().disable()
        .formLogin().disable()
        .csrf().disable()
        .authorizeExchange()
        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        .pathMatchers("/auth/register", "/auth/login").permitAll()
        .pathMatchers("/**").authenticated()
        .anyExchange().authenticated()
        .and()
      //  .authenticationManager(authenticationManager)
     //   .securityContextRepository(securityContextRepository)
        .build();
	    
	}
	
	@Bean
	public BCryptPasswordEncoder createBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
