package com.github.adrian83.mordeczki.auth.model.command;

import javax.validation.constraints.NotEmpty;

public record RefreshTokensCommand(@NotEmpty String refreshToken) {
}
