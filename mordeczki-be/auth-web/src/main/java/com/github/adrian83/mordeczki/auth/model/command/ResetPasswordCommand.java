package com.github.adrian83.mordeczki.auth.model.command;

public record ResetPasswordCommand(String email, String oldPassword, String newPassword) {}
