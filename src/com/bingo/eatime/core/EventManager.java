package com.bingo.eatime.core;

import java.util.HashSet;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class EventManager {

	public static Key addEvent(Event event) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Transaction txn = datastore.beginTransaction();
		Key eventKey;
		try {
			Key restaurantKey = event.getRestaurantKey();
			if (restaurantKey == null) {
				txn.rollback();
				throw new NullKeyException("Restaurant Key is null.");
			}

			Entity eventEntity = new Entity(Event.KIND_EVENT, restaurantKey);
			eventEntity.setProperty(Event.PROPERTY_NAME, event.getName());
			eventEntity.setProperty(Event.PROPERTY_RESTAURANTKEY,
					event.getRestaurantKey());
			eventEntity.setProperty(Event.PROPERTY_TIME, event.getTime());

			eventKey = datastore.put(eventEntity);

			for (Person person : event.getInvites()) {
				Key personKey = person.getKey();
				if (personKey == null) {
					txn.rollback();
					throw new NullKeyException("Person Key is null.");
				}

				Entity personKeyEntity = createPersonKeyEntity(personKey,
						eventKey);
				datastore.put(personKeyEntity);
			}

			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();

				return null;
			}
		}

		return eventKey;
	}

	public static boolean addInvite(Person person, Key eventKey) {
		HashSet<Person> people = new HashSet<Person>();
		people.add(person);
		return addInvites(people, eventKey);
	}

	public static boolean addInvites(Iterable<Person> people, Key eventKey) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Transaction txn = datastore.beginTransaction();
		try {
			for (Person person : people) {
				Key personKey = person.getKey();
				if (personKey == null) {
					txn.rollback();
					throw new NullKeyException("Person Key is null.");
				}

				Entity personKeyEntity = createPersonKeyEntity(personKey,
						eventKey);
				datastore.put(personKeyEntity);
			}
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();

				return false;
			}
		}

		return true;
	}

	protected static Entity createPersonKeyEntity(Key personKey, Key eventKey) {
		Entity personKeyEntity = new Entity(Event.KIND_PERSONKEY, eventKey);
		personKeyEntity.setProperty(Event.PROPERTY_PERSONKEY, personKey);

		return personKeyEntity;
	}

}
