package com.bingo.eatime.core;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public class RestaurantViewer {

	private DatastoreService mDatastoreService;

	public RestaurantViewer() {
		mDatastoreService = DatastoreServiceFactory.getDatastoreService();

	}

}
