package com.bingo.eatime.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bingo.eatime.core.Event;
import com.bingo.eatime.core.EventManager;
import com.bingo.eatime.core.Person;
import com.bingo.eatime.core.PersonManager;
import com.google.appengine.api.datastore.Key;

public class EventAdminServlet extends HttpServlet {

	private static final long serialVersionUID = 8208066016833205326L;
	
	private static final Logger log = Logger.getLogger(EventAdminServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		String eventEvent = req.getParameter("name");
		String restaurantKeyName = req.getParameter("restaurant");
		String creatorUsername = req.getParameter("username");
		String dateString = req.getParameter("date");
		String[] invitesUsername = req.getParameterValues("invite");
		
		Date time = new Date(Integer.valueOf(dateString));
		Person creator = PersonManager.getPerson(creatorUsername);
		HashSet<Person> invites = null;
		
		if (invitesUsername != null) {
			invites = new HashSet<Person>();
			for (String inviteUsername : invitesUsername) {
				Person invite = PersonManager.getPerson(inviteUsername);
				invites.add(invite);
			}
		}
		
		Event event = Event.createEvent(eventEvent, restaurantKeyName, creator, time, invites);
		Key eventKey = EventManager.addEvent(event);
		
		if (eventKey != null) {
			
		} else {
			try {
				PrintWriter writer = resp.getWriter();
			} catch (IOException e) {
				log.log(Level.SEVERE, "Cannot get print writer.", e);
			}
		}
	}

}
