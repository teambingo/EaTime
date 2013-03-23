package com.bingo.eatime.core;

public class Category {

	private String name;

	private Category() {

	}

	public String getName() {
		return name;
	}

	private Category setName(String name) {
		this.name = name;

		return this;
	}

	public static Category createCategory(String name) {
		Category category = new Category().setName(name);

		return category;
	}

}
