package com.bartosztanski.userservice.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandlerUserService extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ErrorMessage> userAlreadyExistsException(
			UserAlreadyExistsException userAlreadyExistsException, WebRequest webRequest) {
		
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_ACCEPTABLE,userAlreadyExistsException.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(message);
	}

}