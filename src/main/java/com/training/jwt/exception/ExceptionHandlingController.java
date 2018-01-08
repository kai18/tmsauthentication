package com.training.jwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.training.jwt.model.StandardResponse;

@ControllerAdvice
public class ExceptionHandlingController {

	@ExceptionHandler(CustomWebException.class)
	public ResponseEntity<StandardResponse> resourceNotFound(RuntimeException exception) {
		StandardResponse response = new StandardResponse();
		// response.setStatus(status);
		response.setMessage(exception.getMessage());
		return new ResponseEntity<StandardResponse>(response, HttpStatus.UNAUTHORIZED);
	}
}
