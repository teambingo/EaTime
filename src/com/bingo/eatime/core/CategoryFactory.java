package com.bingo.eatime.core;

public class CategoryFactory {

	public static Category createCategory(String name) {
		Category category = new Category().setName(name);

		return category;
	}

}
