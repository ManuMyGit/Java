package org.mjjaen.rest.jpacrudrestapi.common.exceptions;

import java.util.Date;

import org.mjjaen.rest.jpacrudrestapi.ExceptionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//Controlador envolvente (AOP) que captará la respuesta de las clases que se indiquen (en este caso, todas aquellas anotadas con RestController) y hará un procesamiento
@ControllerAdvice(annotations=RestController.class)
//@RestController Esta anotación no hace falta
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	/*
	 * Las excepciones se van gestionando desde el primer método al último, y el primer método donde encaje la excepción, ahí
	 * entrará. Por eso, gestión más específica tiene que ir como primer método y la más general como último método
	 */
	@ExceptionHandler({DataNotFoundException.class})
	public final ResponseEntity<Object> handleUserNotFoundException(Exception e, WebRequest req) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), e.getMessage(), req.getDescription(false));
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception e, WebRequest req) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), e.getMessage(), req.getDescription(false));
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//Método de validación, se sobreescribe el de la clase padre ya que este lo único que hace es indicar el código de BAD_REQUEST
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest req) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Validation failed", e.getBindingResult().getAllErrors().toString());
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
}
