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

	private void setKey(Key key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public Key getRestaurantKey() {
		return restaurantKey;
	}

	private void setRestaurantKey(Key restaurantKey) {
		this.restaurantKey = restaurantKey;
	}

	public Person getCreator() {
		return creator;
	}

	private void setCreator(Person creator) {
		this.creator = creator;
	}

	public Date getTime() {
		return time;
	}

	private void setTime(Date time) {
		this.time = time;
	}

	public TreeSet<Person> getInvites() {
		return invites;
	}

	private void setInvites(TreeSet<Person> invites) {
		this.invites = invites;
	}

}
