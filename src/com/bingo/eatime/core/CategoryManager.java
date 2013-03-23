package com.bingo.eatime.core;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;

public class CategoryManager {

	private DatastoreService mDatastoreService;

	public CategoryManager() {
		mDatastoreService = DatastoreServiceFactory.getDatastoreService();
	}

	public boolean addCategory(Category category) {
		Transaction txn = mDatastoreService.beginTransaction();
		try {
			Entity categoryEntity = new Entity(Category.KIND_CATEGORY,
					category.getKey());
			categoryEntity.setProperty(Category.PROPERTY_NAME,
					category.getName());

			mDatastoreService.put(categoryEntity);
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();

				return false;
			}
		}

		return true;
	}

	public boolean addRestaurantToCategory(Key restaurantKey, Key categoryKey) {
		Transaction txn = mDatastoreService.beginTransaction();
		try {

		} finally {
			if (txn.isActive()) {
				txn.rollback();

				return false;
			}
		}

		return true;
	}

}
