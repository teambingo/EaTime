package com.bingo.eatime.core;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.text.WordUtils;

import com.bingo.eatime.util.Gravatar;
import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Link;

public class Person {

	public static final String KIND_PERSON = "person";

	public static final String PROPERTY_FIRSTNAME = "firstname";
	public static final String PROPERTY_LASTNAME = "lastname";
	public static final String PROPERTY_EMAIL = "email";

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
		this.firstName = WordUtils.capitalizeFully(firstName.trim());

		return this;
	}

	public String getLastName() {
		return lastName;
	}

	private Person setLastName(String lastName) {
		this.lastName = WordUtils.capitalizeFully(lastName.trim());

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

	public static Person createPerson(String firstName, String lastName,
			String email) {
		return createPerson(firstName, lastName, new Email(email));
	}

	public static Person createPerson(String firstName, String lastName,
			Email email) {
		Person person = new Person();
		person.setFirstName(firstName).setLastName(lastName).setEmail(email);

		return person;
	}

	public static Person createPerson(Entity entity) {
		if (entity.getKind().equals(KIND_PERSON)) {
			Person person = new Person();
			person.setKey(entity.getKey());
			person.setFirstName((String) entity.getProperty(PROPERTY_FIRSTNAME));
			person.setLastName((String) entity.getProperty(PROPERTY_LASTNAME));
			person.setEmail((Email) entity.getProperty(PROPERTY_EMAIL));

			return person;
		} else {
			throw new EntityKindNotMatchException(
					"Entity Kind must be KIND_PERSON.");
		}
	}

}
