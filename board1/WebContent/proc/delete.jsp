<%@page import="kr.co.board1.service.BoardService"%>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%
	BoardService service = BoardService.getInstance();
	String parent = service.delete(request);
	
	response.sendRedirect("../list.jsp");
%>