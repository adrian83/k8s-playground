package com.github.adrian83.mordeczki.auth.exception;

public class PasswordResetRequiredException extends RuntimeException {

	private static final long serialVersionUID = -3826956839032165008L;

	public PasswordResetRequiredException(String message) {
		super(message);
	}

	public PasswordResetRequiredException(String message, Throwable cause) {
		super(message, cause);
	}
}
