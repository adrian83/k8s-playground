package com.github.adrian83.mordeczki.auth.model.payload;

import java.time.ZonedDateTime;
import java.util.List;

public record TokenDataPayload(String email, List<String> roles, ZonedDateTime expirationDate) {

}
