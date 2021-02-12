package com.github.adrian83.mordeczki.user.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.adrian83.mordeczki.user.model.command.RegisterCommand;
import com.github.adrian83.mordeczki.user.model.command.User;
import com.github.adrian83.mordeczki.user.repository.UserRepository;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired private UserRepository userRepository;

  @PostMapping(path = "/register", consumes = "application/json")
  public Mono<RegisterCommand> status(@Valid @RequestBody RegisterCommand command) {

    var user = User.builder().email("abc").passwordHash("adasdasd").build();
    userRepository.save(user);

    return Mono.just(command);
  }
}
