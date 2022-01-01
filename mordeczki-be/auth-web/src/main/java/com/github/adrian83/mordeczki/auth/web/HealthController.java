package com.github.adrian83.mordeczki.auth.web;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.adrian83.mordeczki.auth.config.SecurityConfig;

@RestController
@RequestMapping(SecurityConfig.HEALTH)
public class HealthController {

	private static final Map<String, String> SUCCESS_BODY = Map.of("status", "ok");

	@GetMapping
	public ResponseEntity<Map<String, String>> health() {
		return ResponseEntity.ok().body(SUCCESS_BODY);
	}
}
