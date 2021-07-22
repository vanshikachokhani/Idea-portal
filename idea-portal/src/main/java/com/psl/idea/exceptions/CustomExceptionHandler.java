package com.psl.idea.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> genericExceptionHandler(Exception br) {
		CustomErrorMessage error = new CustomErrorMessage();
		error.setStatus(400);
		error.setError("Bad Request");
		error.setMessage(br.getMessage());
		return new ResponseEntity<String>(error.toString(), HttpStatus.BAD_REQUEST);
	}

}
