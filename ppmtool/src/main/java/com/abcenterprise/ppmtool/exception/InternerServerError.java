package com.abcenterprise.ppmtool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternerServerError extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InternerServerError() {
		super();
	}

	public InternerServerError(String message) {

		super(message);
	}

	public InternerServerError(String message, Throwable cause) {

		super(message, cause);
	}

	public InternerServerError(Throwable cause) {

		super(cause);
	}
}
