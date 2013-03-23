package com.bingo.eatime.core;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public class EventManager {

	private DatastoreService mDatastore;

	public EventManager() {
		mDatastore = DatastoreServiceFactory.getDatastoreService();
	}

}
