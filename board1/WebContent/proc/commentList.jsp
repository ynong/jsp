<%@page import="com.google.gson.Gson"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="kr.co.board1.vo.BoardVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="kr.co.board1.service.BoardService"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String parent = request.getParameter("parent");
	
	//댓글 가져오기
	BoardService service = BoardService.getInstance();
	ArrayList<BoardVO> list = service.listComment(parent);
	
	Gson gson = new Gson();
	String json = gson.toJson(list);
	
	out.print(json);	
%>