package com.mycompany.jee.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBconnection {

	private static DBconnection instance = null;
	private Statement st;
	private ResultSet rs;
	private Connection con;
	
	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3307/ecomercedb";
	private static String username = "root";
	private static String pass = "";	

	private DBconnection() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, pass);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public static synchronized DBconnection getInstance() {
		if (instance == null) {
			instance = new DBconnection();
		}
		return instance;
	}
	
	
	
	public ResultSet get(String SQL) {
		try {
			st = con.createStatement();
			rs = st.executeQuery(SQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public int update(String SQL) {
		int res = -1;
		try {
			st = con.createStatement();
			res = st.executeUpdate(SQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	public void disconnect() {
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println("Disconnection Failed");
		}
	}
}
