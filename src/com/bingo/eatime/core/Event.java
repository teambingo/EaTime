package com.bingo.eatime.core;

import java.util.Date;
import java.util.TreeSet;

import com.google.appengine.api.datastore.Key;

public final class Event {

	public static final String KIND_EVENT = "event";
	public static final String KIND_PERSONKEY = "person-key";

	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_RESTAURANTKEY = "restaurant-id-key";
	public static final String PROPERTY_CREATOR = "creator";
	public static final String PROPERTY_TIME = "time";
	public static final String PROPERTY_PERSONKEY = "person-id-key";

	private Key key;
	private String name;
	private Key restaurantKey;
	private Person creator;
	private Date time;
	private TreeSet<Person> invites;

	private Event() {

	}

	public Key getKey() {
		return key;
	}

	private Event setKey(Key key) {
		this.key = key;

		return this;
	}

	public String getName() {
		return name;
	}

	private Event setName(String name) {
		this.name = name;

		return this;
	}

	public Key getRestaurantKey() {
		return restaurantKey;
	}

	private Event setRestaurantKey(Key restaurantKey) {
		this.restaurantKey = restaurantKey;

		return this;
	}

	public Person getCreator() {
		return creator;
	}

	private Event setCreator(Person creator) {
		this.creator = creator;

		return this;
	}

	public Date getTime() {
		return time;
	}

	private Event setTime(Date time) {
		this.time = time;

		return this;
	}

	public TreeSet<Person> getInvites() {
		return invites;
	}

	private Event setInvites(TreeSet<Person> invites) {
		this.invites = invites;

		return this;
	}

	protected Event addInvite(Person invite) {
		if (this.invites == null) {
			this.invites = Person.newPeople();
		}

		this.invites.add(invite);

		return this;
	}

	protected Event addInvites(Iterable<Person> invites) {
		if (this.invites == null) {
			this.invites = Person.newPeople();
		}

		for (Person invite : invites) {
			this.invites.add(invite);
		}

		return this;
	}

}
