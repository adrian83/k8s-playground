package com.github.adrian83.mordeczki.auth.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.adrian83.mordeczki.auth.web.health.model.Status;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class IndexController {

  @GetMapping
  public Mono<Status> status() {
    return Mono.just(Status.builder().message("OK").build());
  }
}
