package kr.co.board1.service;

import java.sql.CallableStatement;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.board1.config.DBConfig;
import kr.co.board1.config.SQL;
import kr.co.board1.vo.BoardVO;
import kr.co.board1.vo.MemberVO;

public class BoardService {
	
	private static BoardService service = new BoardService();
	
	public static BoardService getInstance() {
		return service;
	}
	
	private BoardService() {}
	
	public MemberVO getMember(HttpSession session) {
		
		MemberVO vo = (MemberVO) session.getAttribute("member");
		return vo;
	}
	
	public int write(int file, String... args) throws Exception {
		
		Connection conn = DBConfig.getConnection();
		conn.setAutoCommit(false); // 트렌젝션 시작
		
		PreparedStatement psmt = conn.prepareStatement(SQL.INSERT_BOARD);
		psmt.setString(1, args[0]);
		psmt.setString(2, args[1]);
		psmt.setString(3, args[2]);
		psmt.setInt(4, file);
		psmt.setString(5, args[3]);
		
		Statement stmt = conn.createStatement();
		
		psmt.executeUpdate();
		ResultSet rs = stmt.executeQuery(SQL.SELECT_MAX_SEQ);
		
		conn.commit(); // 트렌젝션 적용
		
		int seq = 0;
		if(rs.next()) {
			seq = rs.getInt(1);
		}
	
		rs.close();
		stmt.close();
		psmt.close();
		conn.close();
		
		return seq;
	}
	
	public void fileInsert(int parent, String oldName, String newName) throws Exception {
		
		Connection conn = DBConfig.getConnection();
		
		PreparedStatement psmt = conn.prepareStatement(SQL.INSERT_FILE);
		psmt.setInt(1, parent);
		psmt.setString(2, oldName);
		psmt.setString(3, newName);
		
		psmt.executeUpdate();
		
		psmt.close();
		conn.close();
	}
	
	public void downloadHit(String parent) throws Exception {
		
		Connection conn = DBConfig.getConnection();
		
		PreparedStatement psmt = conn.prepareStatement(SQL.UPDATE_DOWNLOAD_HIT);
		psmt.setString(1, parent);
		
		psmt.executeUpdate();
		psmt.close();
		conn.close();
	}
	
	public int getTotal() throws Exception {
		
		int total = 0;
		Connection conn = DBConfig.getConnection();
		
		Statement stmt = conn.createStatement();
		
		ResultSet rs = stmt.executeQuery(SQL.SELECT_COUNT);
		if(rs.next()) {
			total = rs.getInt(1);
		}
		
		rs.close();
		stmt.close();
		conn.close();
		
		return total;
	}
	
	public int getLimitStart(String pg) {
		int start = 0;
		
		if(pg == null) {
			start = 1;
		} else {
			start = Integer.parseInt(pg);
		}
		
		return (start - 1) * 10;
	}
	
	public int getPageEnd(int total) {
		int pageEnd = 0;
		
		if(total % 10 == 0) {
			pageEnd = total / 10;
		} else {
			pageEnd = (total / 10) + 1;
		}
		
		return pageEnd;
	}
	
	public int getPageCountStart(int total, int limit) {
		return total - limit;
	}
	
	public int[] getPageGroupStartEnd(String pg, int pageEnd) {
		
		int[] groupStartEnd = new int[2];
		int current = 0;
		
		if(pg == null) {
			current = 1;
		} else {
			current = Integer.parseInt(pg);
		}
		
		int currentPage = current;
		int currentPageGroup = (int)Math.ceil(currentPage / 10.0);
		int groupStart = (currentPageGroup - 1) * 10 + 1;
		int groupEnd = currentPageGroup * 10;
		
		if(groupEnd > pageEnd) {
			groupEnd = pageEnd;
		}
		
		groupStartEnd[0] = groupStart;
		groupStartEnd[1] = groupEnd;
		
		return groupStartEnd;
	}
	
	public ArrayList<BoardVO> list(int start) throws Exception {
		
		Connection conn = DBConfig.getConnection();
		
		PreparedStatement psmt = conn.prepareStatement(SQL.SELECT_LIST);
		psmt.setInt(1, start);
		
		ResultSet rs = psmt.executeQuery();
		
		ArrayList<BoardVO> list = new ArrayList<>();
		
		while(rs.next()) {
			BoardVO vo = new BoardVO();
			
			vo.setSeq(rs.getInt(1));
			vo.setParent(rs.getInt(2));
			vo.setComment(rs.getInt(3));
			vo.setCate(rs.getString(4));
			vo.setTitle(rs.getString(5));
			vo.setContent(rs.getString(6));
			vo.setFile(rs.getInt(7));
			vo.setHit(rs.getInt(8));
			vo.setUid(rs.getString(9));
			vo.setRegip(rs.getString(10));
			vo.setRdate(rs.getString(11));
			vo.setNick(rs.getString(12));
			
			list.add(vo);
		}
		
		rs.close();
		psmt.close();
		conn.close();
		
		return list;
	}
	
	public void updateHit(int seq) throws Exception {
		
		Connection conn = DBConfig.getConnection();
		
		PreparedStatement psmt = conn.prepareStatement(SQL.UPDATE_HIT);
		psmt.setInt(1, seq);
		
		psmt.executeUpdate();
		psmt.close();
	}
	
	public BoardVO view(HttpServletRequest request) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		String seq = request.getParameter("seq");
		
		Connection conn = DBConfig.getConnection();
		
		PreparedStatement psmt = conn.prepareStatement(SQL.SELECT_VIEW_WITH_FILE);
		psmt.setString(1, seq);

		ResultSet rs = psmt.executeQuery();
		
		
		BoardVO vo = new BoardVO();
		
		if(rs.next()) {
			vo.setSeq(rs.getInt(1));
			vo.setParent(rs.getInt(2));
			vo.setComment(rs.getInt(3));
			vo.setCate(rs.getString(4));
			vo.setTitle(rs.getString(5));
			vo.setContent(rs.getString(6));
			vo.setFile(rs.getInt(7));
			vo.setHit(rs.getInt(8));
			vo.setUid(rs.getString(9));
			vo.setRegip(rs.getString(10));
			vo.setRdate(rs.getString(11));
			
			vo.setOldName(rs.getString("oldName"));
			vo.setNewName(rs.getString("newName"));
			vo.setDownload(rs.getInt("download"));
		}
		
		rs.close();
		psmt.close();
		conn.close();
		
		return vo;
	}
	
	public String modify(HttpServletRequest request) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		String title = request.getParameter("subject");
		String content = request.getParameter("content");
		String seq = request.getParameter("seq");
		
		Connection conn = DBConfig.getConnection();
		
		PreparedStatement psmt = conn.prepareStatement(SQL.UPDATE_BOARD);
		
		psmt.setString(1, title);
		psmt.setString(2, content);
		psmt.setString(3, seq);
		
		psmt.executeUpdate();
		
		psmt.close();
		conn.close();
		
		return seq;
	}
	
	public String delete(HttpServletRequest request) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		String seq = request.getParameter("seq");
		String parent = request.getParameter("parent");
		
		Connection conn = DBConfig.getConnection();
		
		PreparedStatement psmt = conn.prepareStatement(SQL.DELETE_BOARD);
		
		psmt.setString(1, seq);
		psmt.executeUpdate();
			
		psmt.close();
		
		return parent;
	}
	
	public String insertComment(HttpServletRequest request) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		String parent = request.getParameter("parent");
		String content = request.getParameter("comment");
		String uid = request.getParameter("uid");
		String regip = request.getRemoteAddr();
			
		Connection conn = DBConfig.getConnection();
		
		CallableStatement call = conn.prepareCall(SQL.INSERT_COMMENT);
		
		call.setString(1, parent);
		call.setString(2, content);
		call.setString(3, uid);
		call.setString(4, regip);
		
		call.execute();
		
		call.close();
		conn.close();
		
		return parent;
	}
	
	public ArrayList<BoardVO> listComment(String parent) throws Exception {
		
		Connection conn = DBConfig.getConnection();
		
		PreparedStatement psmt = conn.prepareStatement(SQL.SELECT_COMMENT);
		psmt.setString(1, parent);
		
		ResultSet rs = psmt.executeQuery();
		
		ArrayList<BoardVO> list = new ArrayList<>();
		
		while(rs.next()) {
			BoardVO vo = new BoardVO();
			
			vo.setSeq(rs.getInt("seq"));
			vo.setParent(rs.getInt(2));
			vo.setComment(rs.getInt(3));
			vo.setContent(rs.getString(6));
			vo.setFile(rs.getInt(7));
			vo.setHit(rs.getInt(8));
			vo.setUid(rs.getString(9));
			vo.setRegip(rs.getString(10));
			vo.setRdate(rs.getString("rdate"));
			vo.setNick(rs.getString("nick"));
			
			list.add(vo);
		}
		
		rs.close();
		psmt.close();
		conn.close();
		
		return list;
	}
	
	public void updateCommentCount() {}
	


}
