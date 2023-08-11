package com.github.adrian83.mordeczki.auth.exception;

public class EmailAlreadyInUseException extends RuntimeException {

    private static final long serialVersionUID = -2126956839032165008L;

    public EmailAlreadyInUseException(String message) {
        super(message);
    }

    public EmailAlreadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }
}
