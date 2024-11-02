package com.github.adrian83.mordeczki.auth.exception;

public class AccountNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -7343298479879172L;

    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
