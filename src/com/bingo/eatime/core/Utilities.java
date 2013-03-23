package com.bingo.eatime.core;

import java.util.Comparator;
import java.util.TreeSet;

public class Utilities {

	public static String getKeyFromName(String name) {
		String keyName = name.trim().replace(' ', '-').toLowerCase();

		return keyName;
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

}
