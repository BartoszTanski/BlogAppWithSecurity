package com.bartosztanski.userservice.error;

public class InvalidJwtException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2718338875170433471L;

	public InvalidJwtException() {
		super();
	}
	
	public InvalidJwtException(String message) {
		super(message);
	}
	
	public InvalidJwtException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public InvalidJwtException(Throwable cause) {
		super(cause);
	}
	
	public InvalidJwtException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
