package com.bingo.eatime.core;

public class Category {

	public static final String PROPERTY_NAME = "name";

	private String key;
	private String name;

	private Category() {

	}

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	private Category setName(String name) {
		this.name = name;
		this.key = Utilities.getKeyFromName(name);

		return this;
	}

	public static Category createCategory(String name) {
		Category category = new Category().setName(name);

		return category;
	}

}
