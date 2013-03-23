package com.bingo.eatime.core;

import java.io.File;
import java.util.List;

public class RestaurantFactory {

	public static Restaurant createRestaurant(String name,
			List<Category> categories, String location, File image) {
		Restaurant restaurant = new Restaurant().setName(name)
				.setCategories(categories).setLocation(location)
				.setImage(image);

		return restaurant;
	}

	public static Restaurant createRestaurant(String name,
			List<Category> categories, String location, String image) {
		Restaurant restaurant = new Restaurant().setName(name)
				.setCategories(categories).setLocation(location)
				.setImage(image);

		return restaurant;
	}

	public static Restaurant createRestaurant(String name, Category category,
			String location, File image) {
		Restaurant restaurant = new Restaurant().setName(name)
				.addCategory(category).setLocation(location).setImage(image);

		return restaurant;
	}

	public static Restaurant createRestaurant(String name, Category category,
			String location, String image) {
		Restaurant restaurant = new Restaurant().setName(name)
				.addCategory(category).setLocation(location).setImage(image);

		return restaurant;
	}

}
