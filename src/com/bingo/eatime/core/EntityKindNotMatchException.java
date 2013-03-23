package com.bingo.eatime.core;

public class EntityKindNotMatchException extends RuntimeException {

	private static final long serialVersionUID = 1930065171776694898L;

	public EntityKindNotMatchException() {

	}

	public EntityKindNotMatchException(String message) {
		super(message);
	}

	public EntityKindNotMatchException(Throwable cause) {
		super(cause);
	}

	public EntityKindNotMatchException(String message, Throwable cause) {
		super(message, cause);
	}

}
