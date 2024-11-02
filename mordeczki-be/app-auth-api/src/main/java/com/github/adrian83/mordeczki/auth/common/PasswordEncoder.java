package com.github.adrian83.mordeczki.auth.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public String encode(String rawText) {
        return bCryptPasswordEncoder.encode(rawText);
    }

    public boolean matches(String rawText, String encodedText) {
        return bCryptPasswordEncoder.matches(rawText, encodedText);
    }
}
