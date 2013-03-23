package com.bingo.eatime.core;

import java.util.Comparator;
import java.util.TreeSet;

import com.google.appengine.api.datastore.Entity;
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

	private Category setKey(Key key) {
		this.key = key;

		return this;
	}

	private Category setKey(String name) {
		this.key = KeyFactory.createKey(KIND_CATEGORY,
				Utilities.getKeyFromName(name));

		return this;
	}

	public String getName() {
		return name;
	}

	private Category setName(String name) {
		this.name = name;

		return this;
	}

	protected static TreeSet<Category> newCategories() {
		TreeSet<Category> categories = new TreeSet<Category>(
				new Comparator<Category>() {
					@Override
					public int compare(Category o1, Category o2) {
						return o1.getName().compareTo(o2.getName());
					}
				});

		return categories;
	}

	public static Category createCategory(String name) {
		Category category = new Category();
		category.setName(name).setKey(name);

		return category;
	}

	public static Category createCategory(Entity entity) {
		Category category = new Category();
		category.setKey(entity.getKey());
		category.setName((String) entity.getProperty(PROPERTY_NAME));

		return category;
	}

	public static TreeSet<Category> createCategories(Iterable<Entity> entities) {
		TreeSet<Category> categories = newCategories();

		for (Entity entity : entities) {
			categories.add(createCategory(entity));
		}

		return categories;
	}

}
