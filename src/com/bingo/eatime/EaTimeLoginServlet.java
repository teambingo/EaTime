package com.bingo.eatime;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EaTimeLoginServlet extends HttpServlet {

	private static final long serialVersionUID = 4588543340482590495L;
	
	private static final Logger log = Logger.getLogger(EaTimeLoginServlet.class.getName());

	public void doPost(HttpServletRequest req, HttpServletResponse resp) {

		// System.out.println("Success");
		String user = req.getParameter("user");
		String password = req.getParameter("pwd");
		HttpSession session = req.getSession();
		if ((user.equals("ryan") && password.equals("crd"))
				|| (user.equals("kevin") && password.equals("kevin"))) {
			try {
				String username = user;
				session.setAttribute("loginStatus", "true");
				session.setAttribute("user", username);
				resp.sendRedirect("/eatime");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			session.setAttribute("loginStatus", "false");
			try {
				resp.sendRedirect("/login.jsp");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
