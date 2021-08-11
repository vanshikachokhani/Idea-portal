package com.psl.idea.exceptions;

public class UnauthorizedException extends RuntimeException {

	private static final long serialVersionUID = 401L;

	public UnauthorizedException(String message) {
        super(message);
    }

}
