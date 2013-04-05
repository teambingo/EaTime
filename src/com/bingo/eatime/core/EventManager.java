package com.bingo.eatime.core;

import java.util.Date;
import java.util.HashSet;
import java.util.TreeSet;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.TransactionOptions;

public class EventManager {

	public synchronized static Key addEvent(Event event) {
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
			
			if (event.getJoins() != null) {
				for (Person person : event.getJoins()) {
					Key personKey = person.getKey();
					if (personKey == null) {
						txn.rollback();
						throw new NullKeyException("Person Key is null.");
					}
					
					Entity joinPersonKeyEntity = createJoinPersonKeyEntity(personKey, eventKey);
					datastore.put(joinPersonKeyEntity);
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

	public synchronized static boolean addInvite(Person person, Key eventKey) {
		HashSet<Person> people = new HashSet<Person>();
		people.add(person);
		
		return addInvites(people, eventKey);
	}
	
	public synchronized static boolean addInvite(Person person, long eventKeyId, String restaurantKeyName) {
		Key eventKey = Event.createKey(eventKeyId, restaurantKeyName);
		
		return addInvite(person, eventKey);
	}

	public synchronized static boolean addInvites(Iterable<Person> people, Key eventKey) {
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
				
				if (isJoined(personKey, eventKey)) {
					txn.rollback();
					throw new PersonAlreadyJoinException("Person " + personKey.getName() + 
							" already joined event " + eventKey.getId() + ".");
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
	
	public synchronized static boolean addInvites(Iterable<Person> people, long eventKeyId, String restaurantKeyName) {
		Key eventKey = Event.createKey(eventKeyId, restaurantKeyName);
		
		return addInvites(people, eventKey);
	}
	
	public synchronized static boolean addJoin(Person person, Key eventKey) {
		HashSet<Person> people = new HashSet<Person>();
		people.add(person);
		
		return addJoins(people, eventKey);
	}
	
	public synchronized static boolean addJoin(Person person, long eventKeyId, String restaurantKeyName) {
		Key eventKey = Event.createKey(eventKeyId, restaurantKeyName);
		
		return addJoin(person, eventKey);
	}

	public synchronized static boolean addJoins(Iterable<Person> people, Key eventKey) {
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

				Entity personKeyEntity = createJoinPersonKeyEntity(personKey, eventKey);
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
	
	public synchronized static boolean addJoins(Iterable<Person> people, long eventKeyId, String restaurantKeyName) {
		Key eventKey = Event.createKey(eventKeyId, restaurantKeyName);
		
		return addJoins(people, eventKey);
	}
	
	public synchronized static boolean isJoined(Key personKey, Key eventKey) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Query q = new Query(Event.KIND_JOIN_PERSONKEY, eventKey);
		Filter personKeyFilter = new FilterPredicate(Event.PROPERTY_PERSONKEY, FilterOperator.EQUAL, personKey);
		q.setFilter(personKeyFilter);
		
		PreparedQuery pq = datastore.prepare(q);
		
		FetchOptions fo = FetchOptions.Builder.withLimit(1);
		if (pq.countEntities(fo) > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public synchronized static Entity getEventEntity(Key eventKey) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		try {
			Entity eventEntity = datastore.get(eventKey);
			
			return eventEntity;
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
	
	public synchronized static Event getEvent(Key eventKey) {
		Entity eventEntity = getEventEntity(eventKey);
		
		if (eventEntity != null) {
			Event event = Event.createEvent(eventEntity);
			
			return event;
		} else {
			return null;
		}
	}

	/**
	 * Get all Event entities of a Restaurant key.
	 * 
	 * @param restaurantKey
	 *            Restaurant key.
	 * @return Iterable of event entities.
	 */
	public synchronized static Iterable<Entity> getEventEntitiesFromRestaurant(
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
	public synchronized static TreeSet<Event> getEventsFromRestaurant(Key restaurantKey) {
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
	public synchronized static Iterable<Entity> getEventEntitiesFromRestaurant(
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
	public synchronized static TreeSet<Event> getEventsFromRestaurant(Key restaurantKey,
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
	public synchronized static Iterable<Entity> getInviteEntities(Key eventKey) {
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
	public synchronized static TreeSet<Person> getInvitePeople(Key eventKey) {
		Iterable<Entity> inviteEntities = getInviteEntities(eventKey);
		if (inviteEntities != null) {
			TreeSet<Person> invitePeople = Person.createPeople(inviteEntities);

			return invitePeople;
		} else {
			return null;
		}
	}
	
	public synchronized static Iterable<Entity> getJoinEntities(Key eventKey) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query q = new Query(Event.KIND_JOIN_PERSONKEY, eventKey);

		PreparedQuery pq = datastore.prepare(q);

		HashSet<Entity> joinEntities = new HashSet<Entity>();
		try {
			for (Entity entity : pq.asIterable()) {
				Key personKey = (Key) entity.getProperty(Event.PROPERTY_PERSONKEY);

				Entity personEntity = datastore.get(personKey);
				joinEntities.add(personEntity);
			}
		} catch (EntityNotFoundException e) {
			return null;
		}

		return joinEntities;
	}

	public synchronized static TreeSet<Person> getJoinPeople(Key eventKey) {
		Iterable<Entity> joinEntities = getJoinEntities(eventKey);
		if (joinEntities != null) {
			TreeSet<Person> joinPeople = Person.createPeople(joinEntities);

			return joinPeople;
		} else {
			return null;
		}
	}
	
	public synchronized static boolean removePersonFromInvites(Key personKey, Key eventKey) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Query q = new Query(Event.KIND_PERSONKEY, eventKey);
		Filter personKeyFilter = new FilterPredicate(Event.PROPERTY_PERSONKEY, 
				FilterOperator.EQUAL, personKey);
		q.setFilter(personKeyFilter);
		
		PreparedQuery pq = datastore.prepare(q);
		try {
			for (Entity entity : pq.asIterable()) {
				datastore.delete(entity.getKey());
			}
		} catch (RuntimeException e) {
			return false;
		}
		
		return true;
	}
	
	public synchronized static boolean joinEvent(Key personKey, Key eventKey) {
		Person person = PersonManager.getPerson(personKey);
		boolean addResult = addJoin(person, eventKey);
		if (addResult) {
			boolean removeResult = removePersonFromInvites(personKey, eventKey);
			return removeResult;
		} else {
			return addResult;
		}
	}

	protected synchronized static Entity createPersonKeyEntity(Key personKey, Key eventKey) {
		Entity personKeyEntity = new Entity(Event.KIND_PERSONKEY, personKey.getName(), eventKey);
		personKeyEntity.setProperty(Event.PROPERTY_PERSONKEY, personKey);

		return personKeyEntity;
	}
	
	protected synchronized static Entity createJoinPersonKeyEntity(Key personKey, Key eventKey) {
		Entity joinPersonKeyEntity = new Entity(Event.KIND_JOIN_PERSONKEY, personKey.getName(), eventKey);
		joinPersonKeyEntity.setProperty(Event.PROPERTY_PERSONKEY, personKey);
		
		return joinPersonKeyEntity;
	}

}
