package com.github.adrian83.mordeczki.auth.web;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.github.adrian83.mordeczki.auth.model.command.LoginCommand;
import com.github.adrian83.mordeczki.auth.model.command.RegisterCommand;
import com.github.adrian83.mordeczki.auth.model.command.User;
import com.github.adrian83.mordeczki.auth.repository.UserRepository;


import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private static final String SECRET = "afasdgafdgwuehr23904u2rhuwqerxd";

  @Autowired private UserRepository userRepository;
  @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;


  @PostMapping(path = "/register", consumes = "application/json")
  public Mono<User> register(@Valid @RequestBody RegisterCommand command) {

	  var encodedPass = bCryptPasswordEncoder.encode(command.getPassword());
	  
    var user = User.builder()
    		.email(command.getEmail())
    		.passwordHash(encodedPass)
    		.build();
    
    var savedUser = userRepository.save(user);

    return Mono.just(savedUser);
  }
  
  @PostMapping(path = "/login", consumes = "application/json")
  public ResponseEntity<User> status(@Valid @RequestBody LoginCommand command) {
	  

    User user = userRepository.findById(command.getEmail()).orElseThrow(() -> new RuntimeException("user not found"));

    String token = JWT.create()
            .withSubject(user.getEmail())
            .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * 60 *60 * 2)))
            .sign(Algorithm.HMAC512(SECRET.getBytes()));
    
    
    return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, token)
            .body(user);
    
  }
  

}
