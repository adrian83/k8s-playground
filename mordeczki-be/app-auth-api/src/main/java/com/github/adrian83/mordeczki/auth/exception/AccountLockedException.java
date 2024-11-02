package com.github.adrian83.mordeczki.auth.exception;

public class AccountLockedException extends RuntimeException {

    private static final long serialVersionUID = -6861846126391231237L;

    public AccountLockedException(String message) {
        super(message);
    }

    public AccountLockedException(String message, Throwable cause) {
        super(message, cause);
    }
}
