<%@page import="kr.co.board1.service.MemberService"%>
<%@page import="kr.co.board1.config.SQL"%>
<%@page import="kr.co.board1.config.DBConfig"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%
	
	MemberService service = MemberService.getInstance();
	service.register(request);
	
	response.sendRedirect("../login.jsp?register=success");
%>