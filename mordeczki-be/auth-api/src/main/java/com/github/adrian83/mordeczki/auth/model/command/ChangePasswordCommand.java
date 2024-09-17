package com.github.adrian83.mordeczki.auth.model.command;

import javax.validation.constraints.NotEmpty;

public record ChangePasswordCommand(@NotEmpty String email, @NotEmpty String oldPassword, @NotEmpty String newPassword) {

    @Override
    public String toString() {
        return "%s[email={}, oldPassword=***, newPassword=***]".formatted(getClass().getSimpleName(), email);
    }

}
