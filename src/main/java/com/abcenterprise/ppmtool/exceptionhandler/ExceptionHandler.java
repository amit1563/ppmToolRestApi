package com.abcenterprise.ppmtool.exceptionhandler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.abcenterprise.ppmtool.exception.BacklogNotFoundException;
import com.abcenterprise.ppmtool.exception.ExceptionDetail;
import com.abcenterprise.ppmtool.exception.InternerServerError;
import com.abcenterprise.ppmtool.exception.ProjectNotFoundException;
import com.abcenterprise.ppmtool.exception.ProjectTaskNotFoundException;
import com.abcenterprise.ppmtool.restexception.ErrorDetail;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
	private MessageSource messageSource;

	@Autowired
	public ExceptionHandler(MessageSource messageSource) {
		this.setMessageSource(messageSource);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(ProjectNotFoundException.class)
	public ResponseEntity<ExceptionDetail> handleProjectNotFound(ProjectNotFoundException ex) {
		String exceptionMessage = ex.getMessage();
		return new ResponseEntity<ExceptionDetail>(new ExceptionDetail(new Date(), ex.getMessage(), exceptionMessage),
				HttpStatus.BAD_REQUEST);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(BacklogNotFoundException.class)
	public ResponseEntity<ExceptionDetail> handleBacklogNotFound(BacklogNotFoundException ex) {
		String exceptionMessage = ex.getMessage();
		return new ResponseEntity<ExceptionDetail>(new ExceptionDetail(new Date(), ex.getMessage(), exceptionMessage),
				HttpStatus.BAD_REQUEST);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(ProjectTaskNotFoundException.class)
	public ResponseEntity<ExceptionDetail> handleBacklogNotFound(ProjectTaskNotFoundException ex) {
		String exceptionMessage = ex.getMessage();
		return new ResponseEntity<ExceptionDetail>(new ExceptionDetail(new Date(), ex.getMessage(), exceptionMessage),
				HttpStatus.BAD_REQUEST);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(InternerServerError.class)
	public ResponseEntity<ErrorDetail> handleInternalServerError(InternerServerError re, WebRequest rq) {
		ErrorDetail elementErrDetail = new ErrorDetail(new Date(), re.getMessage(), rq.getDescription(false));
		return new ResponseEntity<ErrorDetail>(elementErrDetail, HttpStatus.NOT_ACCEPTABLE);
	}

	private void setMessageSource(MessageSource messageSource2) {
		this.messageSource = messageSource;
	}
}
