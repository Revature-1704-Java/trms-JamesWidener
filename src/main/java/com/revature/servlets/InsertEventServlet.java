package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.dao.TRDAO;

public class InsertEventServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		String location = request.getParameter("location");
		String description = request.getParameter("description");
		String startYear = request.getParameter("startYear");
		String startMonth = request.getParameter("startMonth");
		String startDay = request.getParameter("startDay");
		String endYear = request.getParameter("endYear");
		String endMonth = request.getParameter("endMonth");
		String endDay = request.getParameter("endDay");
		String hoursMissed = request.getParameter("hoursMissed");
		String eventType = request.getParameter("eventType");
		String gradingFormat = request.getParameter("gradingFormat");
		String passingGrade = request.getParameter("passingGrade");
		String amountRequested = request.getParameter("amountRequested");
		String justification = request.getParameter("justification");
		
		TRDAO dao = new TRDAO();
		int employeeID = dao.employeeLogIn(email, password);
		String employee = Integer.toString(employeeID);
		
		response.setContentType("text/html");  
		response.setCharacterEncoding("UTF-8"); 
		
		if (employeeID != -1) {
			dao.insertRequest(location, description, startYear, startMonth, startDay, endYear, endMonth, endDay, hoursMissed, eventType, gradingFormat, passingGrade, employee, amountRequested, justification);
			response.getWriter().write("Request submitted");
		}
		else
			response.getWriter().write("Not logged in.");
	}

}
