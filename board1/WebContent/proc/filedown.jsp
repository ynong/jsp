<%@page import="kr.co.board1.vo.BoardVO"%>
<%@page import="kr.co.board1.service.BoardService"%>
<%@page import="java.io.BufferedOutputStream"%>
<%@page import="java.io.BufferedInputStream"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%
	request.setCharacterEncoding("utf-8");

	String seq = request.getParameter("seq");
	String newName = request.getParameter("newName");
	String oldName = request.getParameter("oldName"); // 원래 파일명

	// 파일 다운로드 카운트 업데이트
	BoardService service = BoardService.getInstance();
	service.downloadHit(seq);
	
	// 파일 경로 구하기
	String path = request.getServletContext().getRealPath("/data");
	File file = new File(path+"/"+newName);
	String name = new String(oldName.getBytes("utf-8"), "iso-8859-1");

	// response 준비 작업
	response.setContentType("application/octet-stream");
	response.setHeader("Content-Disposition", "attachment; filename="+name);
	response.setHeader("Content-Transfer-Encoding", "binary");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "private");
	
	// 스트림 연결(file ---- response객체)
	BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
	BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
	
	byte b[] = new byte[1024];
	
	int read = 0;
	while(true) {
		// Input스트림으로 데이터 읽어오기
		read = bis.read(b);
		if(read == -1) {
			break;
		}
		
		// Output스트림으로 데이터 쓰기
		bos.write(b, 0, read);
	}
	
	bos.flush();
	bos.close();
	bis.close();
	
%>