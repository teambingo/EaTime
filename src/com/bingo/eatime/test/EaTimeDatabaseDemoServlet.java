package com.bingo.eatime.test;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public class EaTimeDatabaseDemoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		buildDemo();
	}
	
	private synchronized void buildDemo() {
		cleanDatabase();
	}
	
	private synchronized void cleanDatabase() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		
	}

}
