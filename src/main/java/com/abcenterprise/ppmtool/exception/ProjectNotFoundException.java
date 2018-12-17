package com.abcenterprise.ppmtool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -417352653100554089L;

	public ProjectNotFoundException(String message) {
		super(message);
	}

}
