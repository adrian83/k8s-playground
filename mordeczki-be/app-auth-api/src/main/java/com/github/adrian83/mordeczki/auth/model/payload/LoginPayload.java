package com.github.adrian83.mordeczki.auth.model.payload;


public record LoginPayload(String accessToken, String refreshToken){};