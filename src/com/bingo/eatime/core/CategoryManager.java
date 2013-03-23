package com.bingo.eatime.core;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public class CategoryManager {

	public static final String KIND_CATEGORY = "category";

	private DatastoreService mDatastoreService;
	
	public CategoryManager() {
		mDatastoreService = DatastoreServiceFactory.getDatastoreService();
	}

}
