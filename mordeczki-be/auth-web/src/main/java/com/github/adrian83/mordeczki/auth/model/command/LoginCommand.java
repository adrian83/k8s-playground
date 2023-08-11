package com.github.adrian83.mordeczki.auth.model.command;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public record LoginCommand(@Email String email, @NotEmpty String password) {

    @Override
    public String toString() {
        return String.format("%s[email=%s, password=***]", this.getClass().getSimpleName(), email);
    }

}
