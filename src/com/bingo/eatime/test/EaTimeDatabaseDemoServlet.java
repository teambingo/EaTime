package com.bingo.eatime.test;

import java.util.Calendar;
import java.util.HashSet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bingo.eatime.core.Category;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

public class EaTimeDatabaseDemoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		buildDemo();
	}
	
	private synchronized void buildDemo() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();

		cleanDatabase(txn);
		addCategories(txn);
		
		txn.commit();
	}
	
	private synchronized void cleanDatabase(Transaction txn) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		HashSet<Key> allKeys = new HashSet<Key>();
		
		Query q = new Query();
		PreparedQuery pq = datastore.prepare(txn, q);
		
		for (Entity entity : pq.asIterable()) {
			allKeys.add(entity.getKey());
		}
		
		datastore.delete(txn, allKeys);
	}
	
	private synchronized void addCategories(Transaction txn) {
		Category american = Category.createCategory("American");
	}

}
