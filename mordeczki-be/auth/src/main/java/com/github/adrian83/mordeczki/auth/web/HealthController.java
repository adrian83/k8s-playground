package com.github.adrian83.mordeczki.auth.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.adrian83.mordeczki.auth.config.SecurityConfig;

@RestController
@RequestMapping(SecurityConfig.HEALTH)
public class HealthController {

  @GetMapping
  public ResponseEntity<Void> health() {
    return ResponseEntity.ok().build();
  }
}
