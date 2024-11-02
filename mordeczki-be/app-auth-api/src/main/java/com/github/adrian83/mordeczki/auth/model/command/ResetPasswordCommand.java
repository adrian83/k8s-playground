package com.github.adrian83.mordeczki.auth.model.command;

public record ResetPasswordCommand(String token, String password) {

    @Override
    public String toString() {
        return String.format("%s[token=%s, password=***]", this.getClass().getSimpleName(), token);
    }

}
