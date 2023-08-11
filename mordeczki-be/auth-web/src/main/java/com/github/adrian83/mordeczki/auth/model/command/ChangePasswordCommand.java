package com.github.adrian83.mordeczki.auth.model.command;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public record ChangePasswordCommand(@Email String email, @NotEmpty String oldPassword, @NotEmpty String newPassword) {

    @Override
    public String toString() {
        return String.format("%s[email=%s, oldPassword=***, newPassword=***]", this.getClass().getSimpleName(), email);
    }

}
