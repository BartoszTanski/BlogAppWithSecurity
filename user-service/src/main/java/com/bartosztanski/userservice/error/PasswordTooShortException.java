package com.bartosztanski.userservice.error;

public class PasswordTooShortException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 31794069096949345L;

	public PasswordTooShortException() {
		super();
	}
	
	public PasswordTooShortException(String message) {
		super(message);
	}
	
	public PasswordTooShortException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public PasswordTooShortException(Throwable cause) {
		super(cause);
	}
	
	public PasswordTooShortException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
