package com.bingo.eatime.core;

public class NullKeyException extends RuntimeException {

	private static final long serialVersionUID = 7616839790577627916L;

	public NullKeyException() {

	}

	public NullKeyException(String message) {
		super(message);
	}

	public NullKeyException(Throwable cause) {
		super(cause);
	}

	public NullKeyException(String message, Throwable cause) {
		super(message, cause);
	}

}
