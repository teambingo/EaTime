package com.bingo.eatime.core;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class RestaurantStore {

	public static final String KIND_RESTAURANT = "restaurant";

	private DatastoreService mDatastoreService;

	public RestaurantStore() {
		mDatastoreService = DatastoreServiceFactory.getDatastoreService();

	}

	public void add() {
		Entity restaurant = new Entity("restaurant");

	}

}
