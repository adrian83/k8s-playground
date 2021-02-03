package com.github.adrian83.mordeczki.migration.web.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.adrian83.mordeczki.migration.web.health.model.Status;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/health")
public class HealthController {

  @GetMapping
  public Mono<Status> health() {
    return Mono.just(Status.builder().message("OK").build());
  }
}
