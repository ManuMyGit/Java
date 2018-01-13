package org.mjjaen.microservices.currencyconversionservice.common.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(annotations=RestController.class)
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler({LimitsExceededException.class})
	public final ResponseEntity<Object> handleUserNotFoundException(Exception e, WebRequest req) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), e.getMessage(), req.getDescription(false));
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler({Exception.class})
	public final ResponseEntity<Object> handleAllExceptions(Exception e, WebRequest req) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), e.getMessage(), req.getDescription(false));
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
