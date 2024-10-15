package com.github.adrian83.mordeczki.auth.model.payload;

import java.util.List;

public record TokenRequest(String email, List<String> roles) {
};
