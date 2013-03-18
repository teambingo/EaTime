package com.bingo.eatime;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONArray;

public class EaTimeLogin extends HttpServlet {

	private static final long serialVersionUID = 4588543340482590495L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		resp.setContentType("application/json");
		System.out.println("Success");
		String user = req.getParameter("user");
		String password = req.getParameter("pwd");
		JSONArray result = new JSONArray();
		if (user.equals("ryan") && password.equals("crd")) {
			try {
				result.put("1");
				resp.getWriter().print(result);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				result.put("0");
				resp.getWriter().print(result);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
