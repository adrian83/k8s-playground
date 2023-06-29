package com.github.adrian83.mordeczki.auth.model.command;

public record ChangePasswordCommand(String email, String oldPassword, String newPassword) {
}
