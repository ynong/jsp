<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%
	request.setCharacterEncoding("utf-8");

	String email = request.getParameter("email");
	
	final String HOST = "jdbc:mysql://192.168.0.126:3306/yjs";
	final String USER = "yjs";
	final String PASS = "1234";
	
	Class.forName("com.mysql.jdbc.Driver");
	
	Connection conn = DriverManager.getConnection(HOST, USER, PASS);
	
	Statement stmt = conn.createStatement();
	
	ResultSet rs = stmt.executeQuery("select count(*) from `JSP_MEMBER` where email='"+email+"'");
	
	int count = 0;
	
	if(rs.next()) {
		count = rs.getInt(1);
	}
	
	rs.close();
	stmt.close();
	conn.close();
	
	JSONObject json = new JSONObject();
	json.put("result", count);
	
	out.println(json);
%>