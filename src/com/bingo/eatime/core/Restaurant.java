package com.bingo.eatime.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {

	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_CATEGORIES = "categories";
	public static final String PROPERTY_LOCATION = "location";
	// public static final String PROPERTY_IMAGE = "image_path";

	private String name;
	private List<Category> categories;
	private String location;

	// private File image;

	private Restaurant() {

	}

	public String getName() {
		return name;
	}

	private Restaurant setName(String name) {
		this.name = name;

		return this;
	}

	public List<Category> getCategories() {
		return categories;
	}

	private Restaurant setCategories(List<Category> categories) {
		this.categories = categories;

		return this;
	}

	private Restaurant addCategory(Category category) {
		if (this.categories == null) {
			this.categories = new ArrayList<Category>();
		}

		this.categories.add(category);

		return this;
	}

	public String getLocation() {
		return location;
	}

	private Restaurant setLocation(String location) {
		this.location = location;

		return this;
	}

	// public File getImage() {
	// return image;
	// }

	// /**
	// * Set image of this restaurant.
	// *
	// * @param image
	// * File object representing the image file in the file system.
	// */
	// private Restaurant setImage(File image) {
	// this.image = image;
	//
	// return this;
	// }

	// /**
	// * Set image of this restaurant.
	// *
	// * @param image
	// * String of the image file path in the file system.
	// */
	// private Restaurant setImage(String image) {
	// return setImage(new File(image));
	// }

	public static Restaurant createRestaurant(String name,
			List<Category> categories, String location) {
		Restaurant restaurant = new Restaurant().setName(name)
				.setCategories(categories).setLocation(location);

		return restaurant;
	}

	public static Restaurant createRestaurant(String name, Category category,
			String location) {
		Restaurant restaurant = new Restaurant().setName(name)
				.addCategory(category).setLocation(location);

		return restaurant;
	}

}
