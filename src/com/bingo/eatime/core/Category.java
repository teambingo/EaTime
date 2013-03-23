package com.bingo.eatime.core;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class Category {

	public static final String KIND_CATEGORY = "category";
	public static final String KIND_RESTAURANTKEY = "restaurant-key";

	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_RESTAURANTKEY = "restaurant-id-key";

	private Key key;
	private String name;

	private Category() {

	}

	public Key getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	private Category setName(String name) {
		this.name = name;
		this.key = KeyFactory.createKey(KIND_CATEGORY,
				Utilities.getKeyFromName(name));

		return this;
	}

	public static Category createCategory(String name) {
		Category category = new Category().setName(name);

		return category;
	}

}
