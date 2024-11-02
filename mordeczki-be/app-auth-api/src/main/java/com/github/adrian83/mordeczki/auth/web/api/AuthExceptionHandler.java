package com.github.adrian83.mordeczki.auth.web.api;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.github.adrian83.mordeczki.auth.exception.EmailAlreadyInUseException;
import com.github.adrian83.mordeczki.auth.exception.InvalidUsernameOrPasswordException;
import com.github.adrian83.mordeczki.auth.web.model.response.ErrorResponse;


public class AuthExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthExceptionHandler.class);

	public static ResponseEntity<Object> handleError(Throwable t) {
		LOGGER.info("Exception: {}", t.getMessage());
		t.printStackTrace();

		if (t instanceof WebExchangeBindException ex) {
			var constraints = fromBindingErrors(ex.getAllErrors());
			if (!constraints.isEmpty()) {
				return ResponseEntity.badRequest().body(constraints);
			}

		} else if (t instanceof InvalidUsernameOrPasswordException ex) {
			var constr = new ValidationConstraint("password", "Invalid", ex.getMessage());
			return ResponseEntity.badRequest().body(Collections.singletonList(constr));

		} else if(t instanceof EmailAlreadyInUseException ex) {
			var constr = new ValidationConstraint("email", "InUse", ex.getMessage());
			return ResponseEntity.badRequest().body(Collections.singletonList(constr));
		}

		return ResponseEntity.internalServerError().body(new ErrorResponse("internal server error"));
	}

	private static List<ValidationConstraint> fromBindingErrors(List<ObjectError> errors) {
		return errors.stream()
				.map(oe -> oe instanceof FieldError ? (FieldError) oe : null)
				.filter(fe -> fe != null)
				.map(fe -> new ValidationConstraint(fe.getField(), fe.getCode(), fe.getDefaultMessage()))
				.toList();
	}

}
