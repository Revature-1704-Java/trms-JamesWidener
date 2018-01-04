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
			
			/*String sql = "SELECT AdminUsername FROM AdminInfo WHERE AdminInfoID = 1";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getString("AdminUsername");
			else return "No result set";*/
			
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
			//return "";
		}
	}
}
