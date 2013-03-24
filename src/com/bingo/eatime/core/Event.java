package com.bingo.eatime.core;

import java.util.Date;
import java.util.TreeSet;

import com.google.appengine.api.datastore.Key;

public final class Event {

	public static final String KIND_EVENT = "event";

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
		
	}

}
