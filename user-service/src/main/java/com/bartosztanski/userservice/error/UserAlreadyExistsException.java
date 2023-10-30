package com.bartosztanski.userservice.error;

public class UserAlreadyExistsException extends Exception {

	private static final long serialVersionUID = -3598133873590518293L;

	public UserAlreadyExistsException() {
		super();
	}
	
	public UserAlreadyExistsException(String message) {
		super(message);
	}
	
	public UserAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public UserAlreadyExistsException(Throwable cause) {
		super(cause);
	}
	
	public UserAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
