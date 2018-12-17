package com.abcenterprise.ppmtool.restexceptionhandler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.abcenterprise.ppmtool.exception.ProjectIdException;
import com.abcenterprise.ppmtool.restexception.DefaultRestException;
import com.abcenterprise.ppmtool.restexception.ErrorDetail;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	private MessageSource messageSource;

	@Autowired
	public CustomResponseEntityExceptionHandler(MessageSource messageSource) {
		super();
		this.setMessageSource(messageSource);
	}

	@ExceptionHandler(ProjectIdException.class)
	public ResponseEntity<ErrorDetail> handleResourceNotFound(ProjectIdException ex) {
		String exceptionMessage = ex.getLocalizedMessage();
		return new ResponseEntity<ErrorDetail>(new ErrorDetail(new Date(), ex.getMessage(), exceptionMessage),
				HttpStatus.BAD_REQUEST);
	}
 
	@ExceptionHandler(DefaultRestException.class)
	public ResponseEntity<ErrorDetail> handleResourceNotFound(DefaultRestException ex) {
		String exceptionMessage = ex.getLocalizedMessage();
		return new ResponseEntity<ErrorDetail>(new ErrorDetail(new Date(), ex.getMessage(), exceptionMessage),
				HttpStatus.BAD_REQUEST);
	}
	
	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
