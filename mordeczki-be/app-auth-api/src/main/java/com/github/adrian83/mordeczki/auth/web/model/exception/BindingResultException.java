package com.github.adrian83.mordeczki.auth.web.model.exception;

import java.util.List;

import org.springframework.validation.ObjectError;

public class BindingResultException extends RuntimeException {

    private static final long serialVersionUID = -3826956839032165008L;

    private List<ObjectError> errors;

    public BindingResultException(List<ObjectError> errors) {
	super();
	this.errors = errors;
    }

    public List<ObjectError> getErrors() {
	return errors;
    }

}
