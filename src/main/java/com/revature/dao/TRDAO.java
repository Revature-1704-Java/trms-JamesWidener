package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.revature.util.ConnectionUtil;

public class TRDAO {
	public boolean adminLogIn(String username, String password) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "SELECT AdminInfoID FROM AdminInfo WHERE AdminUsername = ? AND AdminPassword = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			
			rs = ps.executeQuery();
			
			if (rs.next())
				return true;
			else
				return false;
			
		} catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
			return false;
		}
	}
	
	public int employeeLogIn(String email, String password) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "SELECT EmployeeID FROM Employee WHERE Email = ? AND EmployeePassword = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);
			
			rs = ps.executeQuery();
			
			if (rs.next())
				return rs.getInt("EmployeeID");
			else
				return -1;
			
		} catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
			return -1;
		}
	}
	
	public String listAllEmployees() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT FirstName, LastName, EmployeeID FROM Employee";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			String allEmployees = "";
			boolean firstLoop = true;
			
			while (rs.next()) {
				if (firstLoop) {
					allEmployees += "Employees: " + rs.getInt("EmployeeID") + " " + rs.getString("FirstName") + " " + rs.getString("LastName");
					firstLoop = false;
				}
				else {
					allEmployees += ", " + rs.getInt("EmployeeID") + " " + rs.getString("FirstName") + " " + rs.getString("LastName");
				}
			}
			return allEmployees;
		} catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
			return "Exception thrown";
		}
	}
	
	public String listGradingFormats() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT GradingFormatName, RequiresPresentation, DefaultPassingGrade, GradingFormatID FROM GradingFormat";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			String allFormats = "";
			
			while (rs.next()) {
				allFormats += "<br/>ID: " + rs.getInt("GradingFormatID") + " Name: " + rs.getString("GradingFormatName");
				allFormats += "<br/>Requires presentation: ";
				if (rs.getInt("RequiresPresentation") == 1) allFormats += "Yes"; else allFormats += "No";
				allFormats += "<br/>Default passing grade: " + rs.getString("DefaultPassingGrade");
			}
			return allFormats;
		} catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
			return "Exception thrown";
		}
	}
	
	public String getEmployeeRequests(int employeeID) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT EventLocation, TrainingTimeStart, TrainingTimeEnd, EventType, EventDescription, GradingFormat, "
					+ "PassingGrade, AmountRequested, Justification, TimeRequestSent, RequestState FROM Request WHERE Employee = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, employeeID);
			rs = ps.executeQuery();
			
			String requestInfo = "";
			
			while (rs.next()) {
				requestInfo += "<br/>Event type ID: " + rs.getInt("EventType");
				requestInfo += "<br>Amount requested: " + rs.getInt("AmountRequested");
				requestInfo += "<br/>Description: " + rs.getString("EventDescription");
				requestInfo += "<br/>Location: " + rs.getString("EventLocation");
				requestInfo += "<br/>Justification: " + rs.getString("Justification");
				requestInfo += "<br/>Start date: " + rs.getDate("TrainingTimeStart");
				requestInfo += "<br/>End date: " + rs.getDate("TrainingTimeEnd");
				requestInfo += "<br/>Grading format ID: " + rs.getInt("GradingFormat");
				requestInfo += "<br/>Passing grade: " + rs.getInt("PassingGrade");
				requestInfo += "<br/>Time request was made: " + rs.getDate("TimeRequestSent");
				requestInfo += "<br/>Status: " + rs.getString("RequestState");
			}
			return requestInfo;
		} catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
			return "Exception thrown";
		}
	}
	
	public void insertRequest(String location, String description,
			String startYear, String startMonth, String startDay, String endYear, String endMonth, String endDay, String hoursMissed,
			String eventType, String gradingFormat, String passingGrade,
			String employee, String amountRequested, String justification) {
		
		PreparedStatement ps = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "INSERT INTO Request (EventLocation, TrainingTimeStart, TrainingTimeEnd, "
					+ "EstimatedWorkHoursMissed, EventDescription, EventType, "
					+ "GradingFormat, PassingGrade, Employee, AmountRequested, Justification, TimeRequestSent) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			
			location = location.replace('_', ' ');
			description = description.replace('_', ' ');
			justification = justification.replace('_', ' ');
			
			ps.setString(1, location);
			ps.setDate(2, java.sql.Date.valueOf(startYear + '-' + startMonth + '-' + startDay));
			ps.setDate(3, java.sql.Date.valueOf(endYear + '-' + endMonth + '-' + endDay));
			ps.setString(4, hoursMissed);
			ps.setString(5, description);
			ps.setString(6, eventType);
			ps.setString(7, gradingFormat);
			ps.setString(8, passingGrade);
			ps.setString(9, employee);
			ps.setString(10, amountRequested);
			ps.setString(11, justification);
			ps.setDate(12, java.sql.Date.valueOf(java.time.LocalDate.now()));
			
			ps.executeUpdate();

			
		} catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
		}
	}
}
