package com.bingo.eatime.core;

import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.Key;

public class Person {

	public static final String KIND_PERSON = "person";

	private Key key;
	private String firstName;
	private String lastName;
	private Email email;

}
