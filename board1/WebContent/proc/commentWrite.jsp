<%@page import="kr.co.board1.service.BoardService"%>
<%@page import="kr.co.board1.config.SQL"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="kr.co.board1.config.DBConfig"%>
<%@page import="java.sql.Connection"%>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%
	BoardService service = BoardService.getInstance();
	String parent = service.insertComment(request);
	
	response.sendRedirect("../view.jsp?seq="+parent);
%>