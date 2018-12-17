package com.abcenterprise.ppmtool.restexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFound extends RuntimeException{

	private static final long serialVersionUID = 305715274667357822L;

	public ResourceNotFound() {
		super();
	}
	
	public ResourceNotFound(String message) {
		
		super(message);
	}
}
