package com.psl.idea.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends Exception {

	private static final long serialVersionUID = 403L;

	public ForbiddenException(String message) {
		super(message);
	}

	
}
