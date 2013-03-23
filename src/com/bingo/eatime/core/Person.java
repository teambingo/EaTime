package com.bingo.eatime.core;

import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.Key;

public class Person {

	public static final String KIND_PERSON = "person";

	private Key key;
	private String firstName;
	private String lastName;
	private Email email;

	private Person() {

	}

	public Key getKey() {
		return key;
	}

	private void setKey(Key key) {
		this.key = key;
	}

	public String getFirstName() {
		return firstName;
	}

	private void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	private void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Email getEmail() {
		return email;
	}

	private void setEmail(Email email) {
		this.email = email;
	}

}
