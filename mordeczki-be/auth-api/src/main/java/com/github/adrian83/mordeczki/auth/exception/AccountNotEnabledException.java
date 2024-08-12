package com.github.adrian83.mordeczki.auth.exception;

public class AccountNotEnabledException extends RuntimeException {

    private static final long serialVersionUID = -3826956887032165008L;

    public AccountNotEnabledException(String message) {
        super(message);
    }

    public AccountNotEnabledException(String message, Throwable cause) {
        super(message, cause);
    }
}
