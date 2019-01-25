<%@page import="kr.co.board1.service.MemberService"%>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%
	MemberService.getInstance().logout(session, response);
%>