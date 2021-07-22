package com.psl.idea.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<String> badRequestExceptionHandler(HttpMessageNotReadableException mnr) {
		CustomErrorMessage error = new CustomErrorMessage();
		error.setStatus(400);
		error.setError("Bad Request");
		error.setMessage(mnr.getMessage());
		return new ResponseEntity<String>(error.toString(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<String> methodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException mns) {
		CustomErrorMessage error = new CustomErrorMessage();
		error.setStatus(405);
		error.setError("Method Not Allowed");
		error.setMessage(mns.getMessage());
		return new ResponseEntity<String>(error.toString(), HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> genericExceptionHandler(Exception e) {
		CustomErrorMessage error = new CustomErrorMessage();
		error.setStatus(500);
		error.setError("Internal Server Error");
		error.setMessage(e.getMessage());
		return new ResponseEntity<String>(error.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<String> NotAuthorizedExceptionHandler (UnauthorizedException ex){
		CustomErrorMessage error = new CustomErrorMessage();
		error.setStatus(401);
		error.setError("UNAUTHORIZED");
		error.setMessage(ex.getMessage());

	        return new ResponseEntity<String>(error.toString(), HttpStatus.UNAUTHORIZED);
		
		
	}

}
