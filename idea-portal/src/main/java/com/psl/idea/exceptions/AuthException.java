package com.psl.idea.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthException extends RuntimeException {
	
	private static final long serialVersionUID = 4011L;

	public AuthException(String message) {
		super(message);
	}
}
