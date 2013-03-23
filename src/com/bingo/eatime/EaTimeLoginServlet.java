package com.bingo.eatime;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.labs.repackaged.org.json.JSONArray;

public class EaTimeLoginServlet extends HttpServlet {

	private static final long serialVersionUID = 4588543340482590495L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		
		System.out.println("Success");
		String user = req.getParameter("user");
		String password = req.getParameter("pwd");
		HttpSession session=req.getSession();
		if (user.equals("ryan") && password.equals("crd")) {
			try {
				String username="ryan";
				session.setAttribute("loginStatus", "true");
				session.setAttribute("user", username);
				resp.sendRedirect("/eatime");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			session.setAttribute("loginStatus", "false");
			try {
				resp.sendRedirect("/login.jsp");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
