package com.github.adrian83.mordeczki.auth.exception;

public class TokenVerificationException extends RuntimeException {

	private static final long serialVersionUID = 3982232865997617360L;

	public TokenVerificationException(String message, Throwable cause) {
		super(message, cause);
	}

}
