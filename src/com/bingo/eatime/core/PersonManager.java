package com.bingo.eatime.core;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class PersonManager {

	public static Key addPerson(Person person) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Transaction txn = datastore.beginTransaction();
		Key personKey;
		try {
			Entity personEntity = new Entity(Person.KIND_PERSON);
			personEntity.setProperty(Person.PROPERTY_USERNAME,
					person.getUsername());
			personEntity.setProperty(Person.PROPERTY_FIRSTNAME,
					person.getFirstName());
			personEntity.setProperty(Person.PROPERTY_LASTNAME,
					person.getLastName());
			personEntity.setProperty(Person.PROPERTY_EMAIL, person.getEmail());

			personKey = datastore.put(personEntity);

			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();

				return null;
			}
		}

		return personKey;
	}

	/**
	 * Get Person Entity from Person Key.
	 * 
	 * @param personKey
	 *            Key of Person.
	 * @return Entity object.
	 * @throws EntityNotFoundException
	 */
	public static Entity getPersonEntity(Key personKey)
			throws EntityNotFoundException {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity personEntity = datastore.get(personKey);

		return personEntity;
	}

	/**
	 * Get Person object from Person Key.
	 * 
	 * @param personKey
	 *            Key of Person.
	 * @return Person object, Null if not found.
	 */
	public static Person getPerson(Key personKey) {
		try {
			Entity personEntity = getPersonEntity(personKey);
			Person person = Person.createPerson(personEntity);

			return person;
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
}
