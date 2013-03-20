package com.bingo.eatime;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONArray;

public class EaTimeLoginServlet extends HttpServlet {

	private static final long serialVersionUID = 4588543340482590495L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		
		System.out.println("Success");
		String user = req.getParameter("user");
		String password = req.getParameter("pwd");
		if (user.equals("ryan") && password.equals("crd")) {
			try {
				resp.sendRedirect("/eatime");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			resp.setContentType("text/html");
			try {
				req.getRequestDispatcher("login.html").include(req, resp);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
