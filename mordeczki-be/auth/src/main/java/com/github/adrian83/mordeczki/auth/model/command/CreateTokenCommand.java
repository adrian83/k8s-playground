package com.github.adrian83.mordeczki.auth.model.command;

import java.time.ZonedDateTime;
import java.util.List;

public record CreateTokenCommand(String email, List<String> roles, ZonedDateTime expirationDate) {};
