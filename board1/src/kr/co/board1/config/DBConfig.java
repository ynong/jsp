package kr.co.board1.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConfig {

	private static final String HOST = "jdbc:mysql://192.168.0.126:3306/yjs";
	private static final String USER = "yjs";
	private static final String PASS = "1234";

	public static Connection getConnection() throws Exception {

		Class.forName("com.mysql.jdbc.Driver");

		Connection conn = DriverManager.getConnection(HOST, USER, PASS);
		return conn;
	}

}