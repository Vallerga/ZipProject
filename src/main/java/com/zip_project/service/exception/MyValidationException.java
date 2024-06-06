package com.zip_project.service.exception;

import net.jimblackler.jsonschemafriend.SchemaException;

public class MyValidationException extends SchemaException {

	private static final long serialVersionUID = 3312819652347867819L;

	public MyValidationException(String message) {
		super(message);
	}
}