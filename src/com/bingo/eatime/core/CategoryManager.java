package com.bingo.eatime.core;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;

public class CategoryManager {

	public static final String KIND_CATEGORY = "category";

	private DatastoreService mDatastoreService;

	public CategoryManager() {
		mDatastoreService = DatastoreServiceFactory.getDatastoreService();
	}

	public boolean addCategory(Category category) {
		Transaction txn = mDatastoreService.beginTransaction();
		try {
			Key categoryKey = KeyFactory.createKey(KIND_CATEGORY,
					category.getKey());
			Entity categoryEntity = new Entity(KIND_CATEGORY, categoryKey);
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

}
