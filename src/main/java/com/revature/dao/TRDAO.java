package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

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
				requestInfo += "<br/>";
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
	
	public String getAwaitingApproval(int employeeID) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		PreparedStatement ps4 = null;
		ResultSet rs4 = null;
		PreparedStatement ps5 = null;
		ResultSet rs5 = null;
		PreparedStatement ps6 = null;
		ResultSet rs6 = null;
		PreparedStatement ps7 = null;
		ResultSet rs7 = null;
		PreparedStatement ps8 = null;
		ResultSet rs8 = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String requestInfo = "";
			boolean isBenCo = false;
			ArrayList<Integer> employeesSupervised = new ArrayList<>();
			ArrayList<Integer> departments = new ArrayList<>();
			ArrayList<Integer> departmentEmployees = new ArrayList<>();
			ArrayList<Integer> requests = new ArrayList<>();
			
			String sql = "SELECT EmployeeID FROM Employee WHERE DirectSupervisor = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, employeeID);
			rs = ps.executeQuery();
			while (rs.next()) {
				employeesSupervised.add(rs.getInt("EmployeeID"));
			}
			
			String sql2 = "SELECT DHDepartment FROM DepartmentHead WHERE DHEmployee = ?";
			ps2 = conn.prepareStatement(sql2);
			ps2.setInt(1, employeeID);
			rs2 = ps2.executeQuery();
			while (rs2.next()) {
				departments.add(rs2.getInt("DHDepartment"));
			}
			
			String sql3 = "SELECT BenCoID FROM BenCo WHERE BenCoEmployee = ?";
			ps3 = conn.prepareStatement(sql3);
			ps3.setInt(1, employeeID);
			rs3 = ps3.executeQuery();
			while (rs3.next()) {
				isBenCo = true;
			}
			
			for (int i = 0; i < employeesSupervised.size(); i++) {
				String sql4 = "SELECT RequestID FROM Request WHERE Employee = ? AND RequestState = 'Submitted'";
				ps4 = conn.prepareStatement(sql4);
				ps4.setInt(1, employeesSupervised.get(i));
				rs4 = ps4.executeQuery();
				while (rs4.next()) {
					requests.add(rs4.getInt("RequestID"));
				}
			}
			
			for (int i = 0; i < departments.size(); i++) {
				String sql5 = "SELECT EmployeeID FROM Employee WHERE Department = ?";
				ps5 = conn.prepareStatement(sql5);
				ps5.setInt(1, departments.get(i));
				rs5 = ps5.executeQuery();
				while (rs5.next()) {
					departmentEmployees.add(rs5.getInt("EmployeeID"));
				}
			}
			
			for (int i = 0; i < departmentEmployees.size(); i++) {
				String sql6 = "SELECT RequestID FROM Request WHERE Employee = ? AND RequestState = 'DepHead'";
				ps6 = conn.prepareStatement(sql6);
				ps6.setInt(1, departmentEmployees.get(i));
				rs6 = ps6.executeQuery();
				while (rs6.next()) {
					requests.add(rs6.getInt("RequestID"));
				}
			}
			
			if (isBenCo) {
				String sql7 = "SELECT RequestID FROM Request WHERE RequestState = 'BenCo'";
				ps7 = conn.prepareStatement(sql7);
				rs7 = ps7.executeQuery();
				while (rs7.next()) {
					requests.add(rs7.getInt("RequestID"));
				}
			}
				
			for (int i = 0; i < requests.size(); i++) {
				String sql8 = "SELECT EventLocation, TrainingTimeStart, TrainingTimeEnd, EstimatedWorkHoursMissed, EventType, EventDescription, GradingFormat, "
						+ "PassingGrade, AmountRequested, Justification, TimeRequestSent, RequestState FROM Request WHERE RequestID = ?";
				ps8 = conn.prepareStatement(sql8);
				ps8.setInt(1, requests.get(i));
				rs8 = ps8.executeQuery();
				while (rs8.next()) {
					requestInfo += "<br/>";
					requestInfo += "<br/>Event type ID: " + rs8.getInt("EventType");
					requestInfo += "<br>Amount requested: " + rs8.getInt("AmountRequested");
					requestInfo += "<br/>Description: " + rs8.getString("EventDescription");
					requestInfo += "<br/>Location: " + rs8.getString("EventLocation");
					requestInfo += "<br/>Justification: " + rs8.getString("Justification");
					requestInfo += "<br/>Start date: " + rs8.getDate("TrainingTimeStart");
					requestInfo += "<br/>End date: " + rs8.getDate("TrainingTimeEnd");
					requestInfo += "<br/>Estimated work hours missed: " + rs8.getInt("EstimatedWorkHoursMissed");
					requestInfo += "<br/>Grading format ID: " + rs8.getInt("GradingFormat");
					requestInfo += "<br/>Passing grade: " + rs8.getInt("PassingGrade");
					requestInfo += "<br/>Time request was made: " + rs8.getDate("TimeRequestSent");
					requestInfo += "<br/>Status: " + rs8.getString("RequestState");
				}
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
	
	public String approve(int employeeID, int givenRequest) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		PreparedStatement ps4 = null;
		ResultSet rs4 = null;
		PreparedStatement ps5 = null;
		ResultSet rs5 = null;
		PreparedStatement ps6 = null;
		ResultSet rs6 = null;
		PreparedStatement ps7 = null;
		ResultSet rs7 = null;
		PreparedStatement ps8 = null;
		ResultSet rs8 = null;
		PreparedStatement psx = null;
		ResultSet rsx = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String requestInfo = "";
			boolean isBenCo = false;
			ArrayList<Integer> employeesSupervised = new ArrayList<>();
			ArrayList<Integer> departments = new ArrayList<>();
			ArrayList<Integer> departmentEmployees = new ArrayList<>();
			ArrayList<Integer> supervisorRequests = new ArrayList<>();
			ArrayList<Integer> departmentRequests = new ArrayList<>();
			ArrayList<Integer> bencoRequests = new ArrayList<>();
			
			String sql = "SELECT EmployeeID FROM Employee WHERE DirectSupervisor = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, employeeID);
			rs = ps.executeQuery();
			while (rs.next()) {
				employeesSupervised.add(rs.getInt("EmployeeID"));
			}
			
			String sql2 = "SELECT DHDepartment FROM DepartmentHead WHERE DHEmployee = ?";
			ps2 = conn.prepareStatement(sql2);
			ps2.setInt(1, employeeID);
			rs2 = ps2.executeQuery();
			while (rs2.next()) {
				departments.add(rs2.getInt("DHDepartment"));
			}
			
			String sql3 = "SELECT BenCoID FROM BenCo WHERE BenCoEmployee = ?";
			ps3 = conn.prepareStatement(sql3);
			ps3.setInt(1, employeeID);
			rs3 = ps3.executeQuery();
			while (rs3.next()) {
				isBenCo = true;
			}
			
			for (int i = 0; i < employeesSupervised.size(); i++) {
				String sql4 = "SELECT RequestID FROM Request WHERE Employee = ? AND RequestState = 'Submitted'";
				ps4 = conn.prepareStatement(sql4);
				ps4.setInt(1, employeesSupervised.get(i));
				rs4 = ps4.executeQuery();
				while (rs4.next()) {
					supervisorRequests.add(rs4.getInt("RequestID"));
				}
			}
			
			for (int i = 0; i < departments.size(); i++) {
				String sql5 = "SELECT EmployeeID FROM Employee WHERE Department = ?";
				ps5 = conn.prepareStatement(sql5);
				ps5.setInt(1, departments.get(i));
				rs5 = ps5.executeQuery();
				while (rs5.next()) {
					departmentEmployees.add(rs5.getInt("EmployeeID"));
				}
			}
			
			for (int i = 0; i < departmentEmployees.size(); i++) {
				String sql6 = "SELECT RequestID FROM Request WHERE Employee = ? AND RequestState = 'DepHead'";
				ps6 = conn.prepareStatement(sql6);
				ps6.setInt(1, departmentEmployees.get(i));
				rs6 = ps6.executeQuery();
				while (rs6.next()) {
					departmentRequests.add(rs6.getInt("RequestID"));
				}
			}
			
			if (isBenCo) {
				String sql7 = "SELECT RequestID FROM Request WHERE RequestState = 'BenCo'";
				ps7 = conn.prepareStatement(sql7);
				rs7 = ps7.executeQuery();
				while (rs7.next()) {
					bencoRequests.add(rs7.getInt("RequestID"));
				}
			}
				
			if (supervisorRequests.contains(givenRequest)) {
				String sqlx = "UPDATE Request SET RequestState = 'DepHead' WHERE RequestID = ?";
				psx = conn.prepareStatement(sqlx);
				psx.setInt(1, givenRequest);
				psx.executeUpdate();
			}
			if (departmentRequests.contains(givenRequest)) {
				String sqlx = "UPDATE Request SET RequestState = 'BenCo' WHERE RequestID = ?";
				psx = conn.prepareStatement(sqlx);
				psx.setInt(1, givenRequest);
				psx.executeUpdate();
			}
			if (bencoRequests.contains(givenRequest)) {
				String sqlx = "UPDATE Request SET RequestState = 'Pending' WHERE RequestID = ?";
				psx = conn.prepareStatement(sqlx);
				psx.setInt(1, givenRequest);
				psx.executeUpdate();
			}
			
			return requestInfo;
		} catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
			return "Exception thrown";
		}
	}
}
