package com.bingo.eatime.core;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.text.WordUtils;

import com.bingo.eatime.util.Gravatar;
import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Link;

public class Person {

	public static final String KIND_PERSON = "person";

	private Key key;
	private String firstName;
	private String lastName;
	private Email email;

	protected Person() {

	}

	public Key getKey() {
		return key;
	}

	private Person setKey(Key key) {
		this.key = key;

		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	private Person setFirstName(String firstName) {
		this.firstName = WordUtils.capitalizeFully(firstName);

		return this;
	}

	public String getLastName() {
		return lastName;
	}

	private Person setLastName(String lastName) {
		this.lastName = WordUtils.capitalizeFully(lastName);

		return this;
	}

	public Email getEmail() {
		return email;
	}

	private Person setEmail(Email email) {
		this.email = email;

		return this;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public String getGravatarUrlString() {
		return Gravatar.getGravatarUrlString(email.getEmail(), true, 100);
	}

	public URL getGravatarUrl() {
		try {
			URL url = new URL(getGravatarUrlString());

			return url;
		} catch (MalformedURLException e) {

		}

		return null;
	}

	public Link getGravatarUrlLink() {
		Link link = new Link(getGravatarUrlString());

		return link;
	}

}
