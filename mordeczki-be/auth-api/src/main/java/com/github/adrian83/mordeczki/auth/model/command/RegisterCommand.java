package com.github.adrian83.mordeczki.auth.model.command;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public record RegisterCommand(@Email String email, @NotEmpty String password) {

    @Override
    public String toString() {
        return "%s[email=%s, password=***]".formatted(getClass().getSimpleName(), email);
    }

}
