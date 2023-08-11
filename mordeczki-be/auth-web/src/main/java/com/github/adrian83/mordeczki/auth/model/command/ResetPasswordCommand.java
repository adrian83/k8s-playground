package com.github.adrian83.mordeczki.auth.model.command;

public record ResetPasswordCommand(String token, String email, String password) {

    @Override
    public String toString() {
        return String.format("%s[token=%s, email=%s, password=***]", this.getClass().getSimpleName(), token, email);
    }

}
