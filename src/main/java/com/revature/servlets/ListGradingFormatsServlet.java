package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.dao.TRDAO;

public class ListGradingFormatsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TRDAO dao = new TRDAO();
		response.setContentType("text/html");  
		response.setCharacterEncoding("UTF-8");

		response.getWriter().write("Grading formats:" + dao.listGradingFormats());
	}
}
