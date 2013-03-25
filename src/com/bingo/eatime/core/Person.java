package com.bingo.eatime.core;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Comparator;
import java.util.TreeSet;

import org.apache.commons.lang3.text.WordUtils;

import com.bingo.eatime.util.Gravatar;
import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Link;

public class Person {

	public static final String KIND_PERSON = "person";

	public static final String PROPERTY_USERNAME = "username";
	public static final String PROPERTY_FIRSTNAME = "firstname";
	public static final String PROPERTY_LASTNAME = "lastname";
	public static final String PROPERTY_EMAIL = "email";

	private Key key;
	private String username;
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

	public String getUsername() {
		return username;
	}

	private Person setUsername(String username) {
		this.username = username.trim();

		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	private Person setFirstName(String firstName) {
		this.firstName = firstName.trim();

		return this;
	}

	public String getLastName() {
		return lastName;
	}

	private Person setLastName(String lastName) {
		this.lastName = lastName.trim();

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
		return getFullName(true);
	}

	public String getFullName(boolean capitalized) {
		if (capitalized) {
			return WordUtils.capitalizeFully(firstName) + " "
					+ WordUtils.capitalizeFully(lastName);
		} else {
			return firstName + " " + lastName;
		}
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

	protected static TreeSet<Person> newPeople() {
		TreeSet<Person> people = new TreeSet<Person>(new Comparator<Person>() {
			@Override
			public int compare(Person o1, Person o2) {
				// Sort first name first, then last name.
				int fnc = o1.getFirstName().compareTo(o2.getFirstName());
				if (fnc != 0) {
					return fnc;
				} else {
					return o1.getLastName().compareTo(o2.getLastName());
				}
			}
		});

		return people;
	}

	// Key will be null if created using this method.
	public static Person createPerson(String username, String firstName,
			String lastName, String email) {
		return createPerson(username, firstName, lastName, new Email(email));
	}

	// Key will be null if created using this method.
	public static Person createPerson(String username, String firstName,
			String lastName, Email email) {
		Person person = new Person();
		person.setUsername(username).setFirstName(firstName)
				.setLastName(lastName).setEmail(email);

		return person;
	}

	public static Person createPerson(Entity entity) {
		if (entity.getKind().equals(KIND_PERSON)) {
			Person person = new Person();
			person.setKey(entity.getKey());
			person.setUsername((String) entity.getProperty(PROPERTY_USERNAME));
			person.setFirstName((String) entity.getProperty(PROPERTY_FIRSTNAME));
			person.setLastName((String) entity.getProperty(PROPERTY_LASTNAME));
			person.setEmail((Email) entity.getProperty(PROPERTY_EMAIL));

			return person;
		} else {
			throw new EntityKindNotMatchException(
					"Entity Kind must be KIND_PERSON.");
		}
	}

	public static TreeSet<Person> createPeople(Iterable<Entity> entities) {
		TreeSet<Person> people = newPeople();
		boolean empty = true;

		for (Entity entity : entities) {
			empty = false;
			Person person = createPerson(entity);
			people.add(person);
		}

		if (empty) {
			return null;
		} else {
			return people;
		}
	}

	@Override
	public String toString() {
		return getFullName(true);
	}

}
