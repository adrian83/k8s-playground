package com.github.adrian83.mordeczki.mailer.auth.registration;

public record RegisterAccountCommand(String email, String token) {
}
