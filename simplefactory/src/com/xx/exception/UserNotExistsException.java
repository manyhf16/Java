package com.xx.exception;

@SuppressWarnings("serial")
public class UserNotExistsException extends RuntimeException {

	public UserNotExistsException() {
		super();
	}

	public UserNotExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotExistsException(String message) {
		super(message);
	}

	public UserNotExistsException(Throwable cause) {
		super(cause);
	}

}
