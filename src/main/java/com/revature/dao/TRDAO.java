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
	
	public boolean employeeLogIn(String email, String password) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "SELECT EmployeeID FROM Employee WHERE Email = ? AND EmployeePassword = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
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
	
	public String listAllEmployees() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT FirstName, LastName FROM Employee";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			String allEmployees = "";
			boolean firstLoop = true;
			
			//if (rs.next() == false) return "No employees in the database";
			
			while (rs.next()) {
				if (firstLoop) {
					allEmployees += "Employees: " + rs.getString("FirstName") + " " + rs.getString("LastName");
					firstLoop = false;
				}
				else {
					allEmployees += ", " + rs.getString("FirstName") + " " + rs.getString("LastName");
				}
			}
			return allEmployees;
		} catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
			return "Exception thrown";
		}
	}
}
