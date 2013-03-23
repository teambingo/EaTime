package com.bingo.eatime.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Restaurant {

	private String key;
	private String name;
	private List<Category> categories;
	private String location;
	private File image;

	public Restaurant() {
		key = UUID.randomUUID().toString();
	}

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public Restaurant setName(String name) {
		this.name = name;

		return this;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public Restaurant setCategories(List<Category> categories) {
		this.categories = categories;

		return this;
	}

	public Restaurant addCategory(Category category) {
		if (this.categories == null) {
			this.categories = new ArrayList<Category>();
		}

		return this;
	}

	public String getLocation() {
		return location;
	}

	public Restaurant setLocation(String location) {
		this.location = location;

		return this;
	}

	public File getImage() {
		return image;
	}

	/**
	 * Set image of this restaurant.
	 * 
	 * @param image
	 *            File object representing the image file in the file system.
	 */
	public Restaurant setImage(File image) {
		this.image = image;

		return this;
	}

	/**
	 * Set image of this restaurant.
	 * 
	 * @param image
	 *            String of the image file path in the file system.
	 */
	public Restaurant setImage(String image) {
		this.image = new File(image);

		return this;
	}

}
