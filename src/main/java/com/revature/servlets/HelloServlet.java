package com.revature.servlets;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

public class HelloServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = request.getParameter("nameEntered");
		response.setContentType("text/html");  
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write("Hello " + name);
		
	}

}
