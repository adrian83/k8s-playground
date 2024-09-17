package com.github.adrian83.mordeczki.auth.model.command;

import javax.validation.constraints.NotEmpty;

public record BothPasswordsCommand(@NotEmpty String oldPassword, @NotEmpty String newPassword) {

    @Override
    public String toString() {
        return "%s[oldPassword=***, newPassword=***]".formatted(getClass().getSimpleName());
    }

}
