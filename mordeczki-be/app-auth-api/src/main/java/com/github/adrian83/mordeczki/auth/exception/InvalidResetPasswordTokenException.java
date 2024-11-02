package com.github.adrian83.mordeczki.auth.exception;

public class InvalidResetPasswordTokenException extends RuntimeException {

    private static final long serialVersionUID = -3826956129032165008L;

    public InvalidResetPasswordTokenException(String message) {
        super(message);
    }

    public InvalidResetPasswordTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
