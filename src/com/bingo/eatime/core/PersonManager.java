package com.bingo.eatime.core;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;

public class PersonManager {
	
	public static Key addPerson(Person person) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	}

}
