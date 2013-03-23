package com.bingo.eatime.core;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;

public class RestaurantManager {

	public static final String KIND_RESTAURANT = "restaurant";

	private DatastoreService mDatastoreService;
	private CategoryManager mCategoryManager;

	public RestaurantManager() {
		mDatastoreService = DatastoreServiceFactory.getDatastoreService();
		mCategoryManager = new CategoryManager();
	}

	/**
	 * Add restaurant to database. It is necessary to check whether the
	 * operation is successful or not.
	 * 
	 * @param restaurant
	 *            Restaurant object, can be created by calling
	 *            Restaurant.createRestaurant.
	 * @return true if succeed, false if failed.
	 */
	public boolean addRestaurant(Restaurant restaurant) {
		Transaction txn = mDatastoreService.beginTransaction();
		try {
			Key restaurantKey = KeyFactory.createKey(KIND_RESTAURANT,
					restaurant.getKey());
			Entity restaurantEntity = new Entity(KIND_RESTAURANT, restaurantKey);
			restaurantEntity.setProperty(Restaurant.PROPERTY_NAME,
					restaurant.getName());
			restaurantEntity.setProperty(Restaurant.PROPERTY_ADDRESS,
					restaurant.getLocation());
			restaurantEntity.setProperty(Restaurant.PROPERTY_PHONENUMBER,
					restaurant.getPhoneNumber());
			// TODO Add category

			mDatastoreService.put(restaurantEntity);
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
				return false;
			}
		}

		return true;
	}
}
