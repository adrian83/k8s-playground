package com.github.adrian83.mordeczki.search.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/health")
public class HealthController {

  @GetMapping
  public ResponseEntity<Void> health() {
    return ResponseEntity.ok().build();
  }
}