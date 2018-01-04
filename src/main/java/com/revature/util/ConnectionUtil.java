package com.revature.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
	public static Connection getConnection() throws SQLException, IOException {
		/*Properties prop = new Properties();
		InputStream in = new FileInputStream("connection.properties");
		prop.load(in);*/
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe"; //prop.getProperty("url");
		String user = "trms_user"; //prop.getProperty("user");
		String password = "trms_password";  //prop.getProperty("password");
		try {
			Class.forName ("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DriverManager.getConnection(url, user, password);
	}
}
