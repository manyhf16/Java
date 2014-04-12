package org.yihang.core.xml.demo;

public class ParseException extends RuntimeException {
	
	private static final long serialVersionUID = -5154231781713692636L;

	public ParseException() {
		super();
	}

	public ParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParseException(String message) {
		super(message);
	}

	public ParseException(Throwable cause) {
		super(cause);
	}

}
