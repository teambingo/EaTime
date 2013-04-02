package com.bingo.eatime.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bingo.eatime.core.Category;
import com.bingo.eatime.core.CategoryManager;
import com.bingo.eatime.core.Event;
import com.bingo.eatime.core.EventManager;
import com.bingo.eatime.core.Person;
import com.bingo.eatime.core.PersonManager;
import com.bingo.eatime.core.Restaurant;
import com.bingo.eatime.core.RestaurantManager;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PhoneNumber;
import com.google.appengine.api.datastore.PostalAddress;

public class EaTimeDatabaseTestServlet extends HttpServlet {

	private static final long serialVersionUID = 2639400166713434665L;
	
	private static final Logger log = Logger.getLogger(EaTimeDatabaseTestServlet.class.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		try {
			resp.setContentType("text/html");
			PrintWriter writer = resp.getWriter();
			writer.println("<html><head></head><body><p>Testing Database...</p></body></html>");
		} catch (IOException e) {
			e.printStackTrace();
		}

		testDb();
//		testRetrieveInvites();
	}
	
	private void testRetrieveInvites() {
		Key eventKey = Event.createKey(1, "happy-china");
		Event event = EventManager.getEvent(eventKey);
		
		for (Person invite : event.getInvites()) {
			log.info("invite: " + invite);
		}
	}

	private void testDb() {
		Key result = null;

		Category categoryChinese = Category.createCategory("Chinese");
		result = CategoryManager.addCategory(categoryChinese);

		log.info("Add category " + categoryChinese + " " + result);

		Category categoryJapanese = Category.createCategory("Japanese");
		result = CategoryManager.addCategory(categoryJapanese);

		log.info("Add category " + categoryJapanese + " " + result);

		Category categoryKorean = Category.createCategory("Korean");
		result = CategoryManager.addCategory(categoryKorean);

		log.info("Add category " + categoryJapanese + " " + result);

		Category categoryIndian = Category.createCategory("Indian");
		result = CategoryManager.addCategory(categoryIndian);

		log.info("Add category " + categoryJapanese + " " + result);

		Category categoryMexico = Category.createCategory("Mexico");
		result = CategoryManager.addCategory(categoryMexico);

		log.info("Add category " + categoryJapanese + " " + result);

		Category categoryThai = Category.createCategory("Thai");
		result = CategoryManager.addCategory(categoryThai);

		log.info("Add category " + categoryJapanese + " " + result);

		Category categoryDessert = Category.createCategory("Dessert");
		result = CategoryManager.addCategory(categoryDessert);

		log.info("Add category " + categoryJapanese + " " + result);

		
		List<Category> restaurantHappyChinaCategories = new ArrayList<Category>();
		restaurantHappyChinaCategories.add(categoryJapanese);
		restaurantHappyChinaCategories.add(categoryChinese);
		Restaurant restaurantHappyChina = Restaurant.createRestaurant(
				"Happy China", restaurantHappyChinaCategories,
				new PostalAddress(
						"219 E State Street, West Lafayette, IN 47906"),
				new PhoneNumber("765-743-1666"));
		result = RestaurantManager.addRestaurant(restaurantHappyChina);

		log.info("Add restaurant " + restaurantHappyChina + " " + result);

		Restaurant restaurantOishi = Restaurant.createRestaurant(
				"Oishi", categoryChinese,
				new PostalAddress(
						"2243 US HWY 52 W, West Lafayette, IN 47906"),
				new PhoneNumber("765-491-0417"));
		result = RestaurantManager.addRestaurant(restaurantOishi);

		log.info("Add restaurant " + restaurantOishi + " " + result);

		
		TreeSet<Category> returnHappyChinaCategories = CategoryManager
				.getRestaurantCategories(restaurantHappyChina.getKey());
		for (Category category : returnHappyChinaCategories) {
			log.info(restaurantHappyChina + " are in " + category + ".");
		}

		TreeSet<Restaurant> categoryChineseRestaurants = CategoryManager
				.getRestaurantsFromCategory(categoryChinese.getKey());

		for (Restaurant restaurant : categoryChineseRestaurants) {
			log.info(restaurant + " is in category " + categoryChinese);
		}

		Person me = Person.createPerson("kevin", "Kaiwen", "Xu", "kevin@kevxu.net");
		Key myKey = PersonManager.addPerson(me);
		
		Person p1 = Person.createPerson("p1", "Random", "Guy", "randomguy1@example.com");
		Key p1Key = PersonManager.addPerson(p1);
		
		Person ryan = Person.createPerson("ryan", "Ryan", "Chen", "chen769@example.com");
		Key ryanKey = PersonManager.addPerson(ryan);
		
		Person p2 = Person.createPerson("p2", "Random", "Guy 2", "randomguy2@example.com");
		Key p2Key = PersonManager.addPerson(p2);
		
		Person p3 = Person.createPerson("p3", "Random", "Guy 3", "randomguy3@example.com");
		Key p3Key = PersonManager.addPerson(p3);
		
		Person p4 = Person.createPerson("p4", "Random", "Guy 4", "randomguy4@example.com");
		Key p4Key = PersonManager.addPerson(p4);
		
		List<Person> invites = new ArrayList<Person>();
		invites.add(p1);
		invites.add(ryan);

		Event sampleEvent = Event.createEvent("Sample Event",
				restaurantHappyChina, me,
				new Date(System.currentTimeMillis()), invites);
		EventManager.addEvent(sampleEvent);
		
		TreeSet<Event> returnSampleEvents = EventManager.getEventsFromRestaurant(restaurantHappyChina.getKey());
		for (Event event : returnSampleEvents) {
			log.info("found event " + event);
		}
	}
}
