package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.dao.TRDAO;

public class ListEmployeesServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		
		TRDAO dao = new TRDAO();
		response.setContentType("text/html");  
		response.setCharacterEncoding("UTF-8");
		
		if (dao.adminLogIn(username, password)) {
			response.getWriter().write("All " + dao.listAllEmployees());
		}
		else {
			response.getWriter().write("Not logged in as admin");
		}
		
	}
}
