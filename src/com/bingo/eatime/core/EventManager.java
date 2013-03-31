package com.bingo.eatime.core;

import java.util.Date;
import java.util.HashSet;
import java.util.TreeSet;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.TransactionOptions;

public class EventManager {

	public static Key addEvent(Event event) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		TransactionOptions options = TransactionOptions.Builder.withXG(true);
		Transaction txn = datastore.beginTransaction(options);
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

			Key creatorKey = event.getCreator().getKey();
			if (creatorKey == null) {
				txn.rollback();
				throw new NullKeyException("Creator Key is null.");
			}
			eventEntity.setProperty(Event.PROPERTY_CREATOR, creatorKey);

			eventKey = datastore.put(eventEntity);

			if (event.getInvites() != null) {
				for (Person person : event.getInvites()) {
					Key personKey = person.getKey();
					if (personKey == null) {
						txn.rollback();
						throw new NullKeyException("Person Key is null.");
					}

					Entity personKeyEntity = createPersonKeyEntity(personKey, eventKey);
					datastore.put(personKeyEntity);
				}
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
	
	public static boolean addInvite(Person person, long eventKeyId) {
		Key eventKey = KeyFactory.createKey(Event.KIND_EVENT, eventKeyId);
		
		return addInvite(person, eventKey);
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

				Entity personKeyEntity = createPersonKeyEntity(personKey, eventKey);
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
	
	public static boolean addInvites(Iterable<Person> people, long eventKeyId) {
		Key eventKey = KeyFactory.createKey(Event.KIND_EVENT, eventKeyId);
		
		return addInvites(people, eventKey);
	}

	/**
	 * Get all Event entities of a Restaurant key.
	 * 
	 * @param restaurantKey
	 *            Restaurant key.
	 * @return Iterable of event entities.
	 */
	public static Iterable<Entity> getEventEntitiesFromRestaurant(
			Key restaurantKey) {
		return getEventEntitiesFromRestaurant(restaurantKey, null);
	}

	/**
	 * Get all Event objects of a Restaurant Key.
	 * 
	 * @param restaurantKey
	 *            Restaurant key.
	 * @return A set of Event object ordered by time. Null if not found.
	 */
	public static TreeSet<Event> getEventsFromRestaurant(Key restaurantKey) {
		return getEventsFromRestaurant(restaurantKey, null);
	}

	/**
	 * Get all Event entities of a Restaurant key based on a specific date.
	 * 
	 * @param restaurantKey
	 *            Restaurant key.
	 * @param date
	 *            A specific date.
	 * @return Iterable of event entities.
	 */
	public static Iterable<Entity> getEventEntitiesFromRestaurant(
			Key restaurantKey, Date date) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query q = new Query(Event.KIND_EVENT, restaurantKey);
		Filter restaurantKeyFilter = new FilterPredicate(
				Event.PROPERTY_RESTAURANTKEY, Query.FilterOperator.EQUAL,
				restaurantKey);

		if (date == null) {
			q.setFilter(restaurantKeyFilter);
		} else {
			Date startDate = Utilities.getStartEndDate(date, true);
			Date endDate = Utilities.getStartEndDate(date, false);

			Filter startDateFilter = new FilterPredicate(Event.PROPERTY_TIME,
					Query.FilterOperator.GREATER_THAN_OR_EQUAL, startDate);
			Filter endDateFilter = new FilterPredicate(Event.PROPERTY_TIME,
					Query.FilterOperator.LESS_THAN_OR_EQUAL, endDate);

			Filter timeFilter = CompositeFilterOperator.and(startDateFilter,
					endDateFilter);
			Filter overallFilter = CompositeFilterOperator.and(
					restaurantKeyFilter, timeFilter);

			q.setFilter(overallFilter);
		}

		PreparedQuery pq = datastore.prepare(q);

		return pq.asIterable();
	}

	/**
	 * Get all Event objects of a Restaurant Key based on a specific date.
	 * 
	 * @param restaurantKey
	 *            Restaurant key.
	 * @param date
	 *            A specific date.
	 * @return A set of Event object ordered by time. Null if not found.
	 */
	public static TreeSet<Event> getEventsFromRestaurant(Key restaurantKey,
			Date date) {
		Iterable<Entity> eventEntities = getEventEntitiesFromRestaurant(
				restaurantKey, date);
		TreeSet<Event> events = Event.createEvents(eventEntities);

		return events;
	}

	/**
	 * Get invite Person entities from an event key.
	 * 
	 * @param eventKey
	 *            Event key.
	 * @return A Iterable of Entity. Null if failed.
	 */
	public static Iterable<Entity> getInviteEntities(Key eventKey) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query q = new Query(Event.KIND_PERSONKEY, eventKey);

		PreparedQuery pq = datastore.prepare(q);

		HashSet<Entity> inviteEntities = new HashSet<Entity>();
		try {
			for (Entity entity : pq.asIterable()) {
				Key personKey = (Key) entity
						.getProperty(Event.PROPERTY_PERSONKEY);

				Entity personEntity = datastore.get(personKey);
				inviteEntities.add(personEntity);
			}
		} catch (EntityNotFoundException e) {
			return null;
		}

		return inviteEntities;
	}

	/**
	 * Get an ordered set of invite Person objects from an event key.
	 * 
	 * @param eventKey
	 *            Event key.
	 * @return A set of invite Person ordered by the name of the person. Null if
	 *         failed.
	 */
	public static TreeSet<Person> getInvitePeople(Key eventKey) {
		Iterable<Entity> inviteEntities = getInviteEntities(eventKey);
		if (inviteEntities != null) {
			TreeSet<Person> invitePeople = Person.createPeople(inviteEntities);

			return invitePeople;
		} else {
			return null;
		}
	}

	protected static Entity createPersonKeyEntity(Key personKey, Key eventKey) {
		Entity personKeyEntity = new Entity(Event.KIND_PERSONKEY, eventKey);
		personKeyEntity.setProperty(Event.PROPERTY_PERSONKEY, personKey);

		return personKeyEntity;
	}

}
