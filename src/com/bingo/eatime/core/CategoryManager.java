package com.bingo.eatime.core;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class CategoryManager {

	private DatastoreService mDatastoreService;

	public CategoryManager() {
		mDatastoreService = DatastoreServiceFactory.getDatastoreService();
	}

	/**
	 * Add category to database.It is necessary to check whether the operation
	 * is successful or not.
	 * 
	 * @param category
	 *            Category object, can be created by calling
	 *            Category.createCategory.
	 * @return true if succeed, false if failed.
	 */
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

	/**
	 * Add restaurant key to a specific category.It is necessary to check
	 * whether the operation is successful or not.
	 * 
	 * @param restaurantKey
	 *            restaurant Key object.
	 * @param categoryKey
	 *            Key object of the category to be added into.
	 * @return true if succeed, false if failed.
	 */
	public boolean addRestaurantToCategory(Key restaurantKey, Key categoryKey) {
		Transaction txn = mDatastoreService.beginTransaction();
		try {
			Entity restaurantKeyEntity = new Entity(
					Category.KIND_RESTAURANTKEY, categoryKey);
			restaurantKeyEntity.setProperty(Category.PROPERTY_RESTAURANTKEY,
					restaurantKey);

			mDatastoreService.put(restaurantKeyEntity);
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
