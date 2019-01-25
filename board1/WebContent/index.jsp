<%@page import="kr.co.board1.vo.MemberVO"%>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%
	
	MemberVO member = (MemberVO)session.getAttribute("member");

	if(member == null){
		//response.sendRedirect("./login.jsp");
		pageContext.forward("./login.jsp");
	} else {
		//response.sendRedirect("./list.jsp");
		pageContext.forward("./list.jsp");
	}


%>