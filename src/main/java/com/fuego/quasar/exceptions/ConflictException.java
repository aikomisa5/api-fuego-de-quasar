package com.fuego.quasar.exceptions;

public class ConflictException extends Exception {

	private static final long serialVersionUID = -5027371875026978440L;

	public ConflictException() {
		super();
	}

	public ConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ConflictException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConflictException(String message) {
		super(message);
	}

	public ConflictException(Throwable cause) {
		super(cause);
	}
}
