package com.bingo.eatime.core;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
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
	 * Create a RestaurantKey Entity for a specific Category Key.
	 * 
	 * @param restaurantKey
	 *            restaurant Key object.
	 * @param categoryKey
	 *            Key object of the category to be added into.
	 * @return Entity of RestaurantKey kind.
	 */
	public static Entity createRestaurantKeyEntity(Key restaurantKey,
			Key categoryKey) {
		Entity restaurantKeyEntity = new Entity(Category.KIND_RESTAURANTKEY,
				categoryKey);
		restaurantKeyEntity.setProperty(Category.PROPERTY_RESTAURANTKEY,
				restaurantKey);

		return restaurantKeyEntity;
	}

	public void getRestaurantCategories(Key restaurantKey) {
		Filter restaurantKeyFilter = new FilterPredicate(
				Category.PROPERTY_RESTAURANTKEY, Query.FilterOperator.EQUAL,
				restaurantKey);

		Query q = new Query(Category.KIND_RESTAURANTKEY)
				.setFilter(restaurantKeyFilter);
		
		PreparedQuery pq = mDatastoreService.prepare(q);

	}

}
