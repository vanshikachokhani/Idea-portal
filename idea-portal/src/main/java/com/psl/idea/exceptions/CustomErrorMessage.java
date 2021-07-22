package com.psl.idea.exceptions;

public class CustomErrorMessage {
	
	private int status;
	private String error;
	private String message;
	
	public CustomErrorMessage() {
		super();
	}

	public CustomErrorMessage(int status, String error, String message) {
		super();
		this.status = status;
		this.error = error;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "{\n\t\"status\": \"" + status + "\",\n\t\"error\": \"" + error + "\",\n\t\"message\": \"" + message + "\"\n}";
	}
}
