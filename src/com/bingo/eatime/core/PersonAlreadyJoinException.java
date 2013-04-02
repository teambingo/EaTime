package com.bingo.eatime.core;

public class PersonAlreadyJoinException extends RuntimeException {

	private static final long serialVersionUID = -2587590249622016395L;

	public PersonAlreadyJoinException() {

	}
	
	public PersonAlreadyJoinException(String msg) {
		super(msg);

	}

	public PersonAlreadyJoinException(Throwable cause) {
		super(cause);
		
	}

	public PersonAlreadyJoinException(String msg, Throwable cause) {
		super(msg, cause);
		
	}

}
