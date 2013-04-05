package com.bingo.eatime.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bingo.eatime.core.Category;
import com.bingo.eatime.core.CategoryManager;
import com.bingo.eatime.core.Person;
import com.bingo.eatime.core.PersonManager;
import com.bingo.eatime.core.Restaurant;
import com.bingo.eatime.core.RestaurantManager;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PhoneNumber;
import com.google.appengine.api.datastore.PostalAddress;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class EaTimeDatabaseDemoServlet extends HttpServlet {
	
	private static final long serialVersionUID = 8886496714256884134L;
	
	private static final Logger log = Logger.getLogger(EaTimeDatabaseDemoServlet.class.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		buildDemo();
		
		try {
			PrintWriter writer = resp.getWriter();
			writer.println("<html><head><title>Demo Database Builder</title></head><body>Finished building demo database.</body></html>");
		} catch (IOException e) {
			log.severe("IOException");
		}
	}
	
	private synchronized void buildDemo() {
		cleanDatabase();
		addRestaurants();
		addPeople();
	}
	
	private synchronized void cleanDatabase() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		HashSet<Key> allKeys = new HashSet<Key>();
		
		Query q = new Query();
		PreparedQuery pq = datastore.prepare(q);
		
		for (Entity entity : pq.asIterable()) {
			allKeys.add(entity.getKey());
		}
		
		datastore.delete(allKeys);
	}
	
	private synchronized void addRestaurants() {
		// Add categories
		List<Category> categories = new ArrayList<Category>();
		
		Category american = Category.createCategory("American");
		Category chinese = Category.createCategory("Chinese");
		Category japanese = Category.createCategory("Japanese");
		Category thai = Category.createCategory("Thai");
		Category breakfast = Category.createCategory("Breakfast");
		Category italian = Category.createCategory("Italian");
		Category openLate = Category.createCategory("Open Late");
		Category vegetarian = Category.createCategory("Vegetarian");
		
		categories.add(american);
		categories.add(chinese);
		categories.add(japanese);
		categories.add(thai);
		categories.add(breakfast);
		categories.add(italian);
		categories.add(openLate);
		categories.add(vegetarian);
		
		for (Category category : categories) {
			CategoryManager.addCategory(category);
		}
		
		// Add restaurants
		categories.clear();
		categories.add(american);
		categories.add(openLate);
		Restaurant domino = Restaurant.createRestaurant("Domino's Pizza", categories, new PostalAddress("330 E. State Street, West Lafayette, IN 47904"), new PhoneNumber("765-743-3000"));
		RestaurantManager.addRestaurant(domino);
		
		categories.clear();
		categories.add(american);
		categories.add(openLate);
		Restaurant madMushroom = Restaurant.createRestaurant("Mad Mushroom", categories, new PostalAddress("320 W State St., West Lafayette, IN 47906"), new PhoneNumber("765-743-5555"));
		RestaurantManager.addRestaurant(madMushroom);
		
		categories.clear();
		categories.add(american);
		Restaurant ajs = Restaurant.createRestaurant("AJ's Burger & Beef", categories, new PostalAddress("134 W State Street, Suite D, West Lafayette, IN 47906"), new PhoneNumber("765-743-1940"));
		RestaurantManager.addRestaurant(ajs);
	}
	
	private synchronized void addPeople() {
		Person kevin = Person.createPerson("kevin", "Kaiwen", "Xu", "kevin@kevxu.net");
		PersonManager.addPerson(kevin);
		
		Person ryan = Person.createPerson("ryan", "Rendong", "Chen", "ryan@example.com");
		PersonManager.addPerson(ryan);
		
		Person brew20k = Person.createPerson("brew20k", "Kevin", "Kinnebrew", "kevin.kinnebrew@gmail.com");
		PersonManager.addPerson(brew20k);
		
		Person christophermllr = Person.createPerson("christophermllr", "Christopher", "Miller", "cmiller0189@gmail.com");
		PersonManager.addPerson(christophermllr);
		
		Person john = Person.createPerson("john", "John", "Doe", "johndoe@example.com");
		PersonManager.addPerson(john);
		
		Person jane = Person.createPerson("jane", "Jane", "Doe", "janedoe@example.com");
		PersonManager.addPerson(jane);
	}

}
