package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
	
	public void insertRequest(String location, String description,
			String startYear, String startMonth, String startDay, String endYear, String endMonth, String endDay, String hoursMissed,
			String eventType, String gradingFormat, String passingGrade,
			String employee, String amountRequested, String justification) {
		
		PreparedStatement ps = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "INSERT INTO Event (EventLocation, TrainingTimeStart, TrainingTimeEnd, "
					+ "EstimatedWorkHoursMissed, EventDescription, EventType, "
					+ "GradingFormat, PassingGrade, Employee, AmountRequested, Justification, TimeSubmitted) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			
			String startDate = "TO_DATE('" + startDay + "/" + startMonth + "/" + startYear + "', 'DD/MM/YYYY')";
			String endDate = "TO_DATE('" + endDay + "/" + endMonth + "/" + endYear + "', 'DD/MM/YYYY')";
			String currentTime = "SYSDATE";
			
			location = location.replace('_', ' ');
			description = description.replace('_', ' ');
			justification = justification.replace('_', ' ');
			
			ps.setString(1, location);
			ps.setString(2, startDate);
			ps.setString(3, endDate);
			ps.setString(4, hoursMissed);
			ps.setString(5, description);
			ps.setString(6, eventType);
			ps.setString(7, gradingFormat);
			ps.setString(8, passingGrade);
			ps.setString(9, employee);
			ps.setString(10, amountRequested);
			ps.setString(11, justification);
			ps.setString(12, currentTime);
			
			ps.executeUpdate();

			
		} catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
		}
	}
}
