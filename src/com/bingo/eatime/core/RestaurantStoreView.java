package com.bingo.eatime.core;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public class RestaurantStoreView {

	private DatastoreService mDatastoreService;

	public RestaurantStoreView() {
		mDatastoreService = DatastoreServiceFactory.getDatastoreService();

	}

}
