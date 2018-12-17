package com.abcenterprise.ppmtool.restexception;

public class InvalidCredentialException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public InvalidCredentialException() {
		super();
	}

	public InvalidCredentialException(String message) {
		super(message);
	}

	public InvalidCredentialException(Throwable cause) {
		super(cause);
	}

}
