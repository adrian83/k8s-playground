package com.github.adrian83.mordeczki.auth.exception;

public class AccountDisabledException extends RuntimeException {

    private static final long serialVersionUID = -3826956887032165008L;

    public AccountDisabledException(String message) {
        super(message);
    }

    public AccountDisabledException(String message, Throwable cause) {
        super(message, cause);
    }
}
