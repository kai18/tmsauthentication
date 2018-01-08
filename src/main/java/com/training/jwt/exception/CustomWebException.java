package com.training.jwt.exception;

public class CustomWebException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String status;
	
	private String message;

	public CustomWebException(String status, String message) {
		super(message);
		this.status = status;
		this.message = message;
	}

}
