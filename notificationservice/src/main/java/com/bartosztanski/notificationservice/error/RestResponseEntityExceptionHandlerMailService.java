package com.bartosztanski.notificationservice.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bartosztanski.notificationservice.model.ErrorMessage;

import jakarta.mail.MessagingException;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandlerMailService extends ResponseEntityExceptionHandler {

	@ExceptionHandler(MessagingException.class)
	public ResponseEntity<ErrorMessage> messagingException(
			MessagingException messagingException, WebRequest webRequest) {
		
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND,messagingException.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}
	
}
