package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.dao.TRDAO;

public class EmployeeLoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		TRDAO dao = new TRDAO();
		
		response.setContentType("text/html");  
		response.setCharacterEncoding("UTF-8"); 
		
		if (dao.employeeLogIn(email, password)) {
			response.getWriter().write("Logged in.");
		}
		else
			response.getWriter().write("Wrong email or password: \"" + email + "\" \"" + password + "\"");
	}

}
