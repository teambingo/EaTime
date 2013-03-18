package com.bingo.eatime;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EaTimeLogin extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		String user = req.getParameter("user");
		String password = req.getParameter("pwd");

		resp.setContentType("text/plain");
		try {
			resp.getWriter().println("Username: " + user);
			resp.getWriter().println("Password: " + password);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
