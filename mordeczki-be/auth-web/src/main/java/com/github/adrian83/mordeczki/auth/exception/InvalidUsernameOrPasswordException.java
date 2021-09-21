package com.github.adrian83.mordeczki.auth.exception;

public class InvalidUsernameOrPasswordException extends RuntimeException {

  private static final long serialVersionUID = -3826956839032165008L;

  public InvalidUsernameOrPasswordException(String message) {
    super(message);
  }

  public InvalidUsernameOrPasswordException(String message, Throwable cause) {
    super(message, cause);
  }
}
