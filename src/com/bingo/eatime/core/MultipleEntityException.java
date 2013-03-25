package com.bingo.eatime.core;

public class MultipleEntityException extends RuntimeException {

	private static final long serialVersionUID = -2568895476377601520L;

	public MultipleEntityException() {

	}

	public MultipleEntityException(String message) {
		super(message);

	}

	public MultipleEntityException(Throwable cause) {
		super(cause);

	}

	public MultipleEntityException(String message, Throwable cause) {
		super(message, cause);

	}

}
