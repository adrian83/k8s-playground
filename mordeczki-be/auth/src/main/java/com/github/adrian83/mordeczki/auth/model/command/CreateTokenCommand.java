package com.github.adrian83.mordeczki.auth.model.command;

import java.util.List;

public record CreateTokenCommand(String email, List<String> roles) {};
