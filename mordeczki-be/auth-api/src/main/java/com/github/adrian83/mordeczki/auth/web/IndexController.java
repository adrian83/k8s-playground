package com.github.adrian83.mordeczki.auth.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {

	@GetMapping
	public ResponseEntity<Void> index() {
		return ResponseEntity.ok().build();
	}
}
