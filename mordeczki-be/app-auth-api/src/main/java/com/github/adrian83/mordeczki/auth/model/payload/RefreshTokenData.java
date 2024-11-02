package com.github.adrian83.mordeczki.auth.model.payload;

import java.time.ZonedDateTime;

public record RefreshTokenData(String email, ZonedDateTime expirationDate) {

}
