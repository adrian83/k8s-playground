package com.github.adrian83.mordeczki.auth.model.payload;

import java.time.ZonedDateTime;


public record LoginPayload(String tokenType, String accessToken, String refreshToken, ZonedDateTime date){};