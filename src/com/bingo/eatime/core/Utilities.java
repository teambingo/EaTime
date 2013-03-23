package com.bingo.eatime.core;

public class Utilities {

	public static String getCategoriesKey(String categoriesName) {
		String keyName = categoriesName.trim().replace(' ', '-').toLowerCase();

		return keyName;
	}

}
