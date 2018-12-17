package com.abcenterprise.ppmtool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectIdException extends RuntimeException {

	public ProjectIdException() {
		super();
	}

	public ProjectIdException(String message) {
		super(message);
	}

}
