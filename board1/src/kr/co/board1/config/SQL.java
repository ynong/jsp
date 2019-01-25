package kr.co.board1.config;

public class SQL {

	public static final String SELECT_TERMS = "SELECT * FROM `JSP_TERMS`";
	
	public static final String INSERT_REGISTER = "insert into `JSP_MEMBER` set uid=?, pass=PASSWORD(?), name=?, nick=?, email=?, hp=?, zip=?, addr1=?, addr2=?, regip=?, rdate=NOW()";
	
	public static final String SELECT_LOGIN = "select * from `JSP_MEMBER` where uid=? and pass=PASSWORD(?)";
	
	public static final String SELECT_MAX_SEQ = "select max(seq) from `JSP_BOARD`";
	
	public static final String INSERT_BOARD = "insert into `JSP_BOARD` set "
											+ "cate='notice',"
											+ "title=?,"
											+ "content=?,"
											+ "uid=?,"
											+ "file=?,"
											+ "regip=?,"
											+ "rdate=NOW()";
	
	public static final String INSERT_FILE = "insert into `JSP_FILE` (parent, oldName, newName, rdate) values (?, ?, ?, NOW())";
	
	public static final String SELECT_COUNT = "select count(*) from `JSP_BOARD` where parent=0;";
	
	public static final String SELECT_LIST = "select b.*, m.nick from `JSP_BOARD` as b join `JSP_MEMBER` as m on b.uid = m.uid where parent=0 order by b.seq desc limit ?, 10";
	
	public static final String SELECT_VIEW = "select * from `JSP_BOARD` where seq=?";
	
	public static final String SELECT_VIEW_WITH_FILE = "select * from `JSP_BOARD` as b left join `JSP_FILE` as f on b.seq = f.parent where b.seq=?";
	
	public static final String UPDATE_HIT = "update `JSP_BOARD` set hit=hit+1 where seq=?";
	
	public static final String DELETE_BOARD = "delete from `JSP_BOARD` where seq=?";
	
	public static final String UPDATE_BOARD = "update `JSP_BOARD` set " 
											+ "title=?,"
											+ "content=?"
											+ " where seq=?";
	
	public static final String INSERT_COMMENT = "call insertComment(?, ?, ?, ?)";
	
	public static final String SELECT_COMMENT = "SELECT B.*, M.nick "
											  + "FROM `JSP_BOARD` AS B, `JSP_MEMBER` AS M "
											  + "WHERE B.uid = M.uid AND parent=? "
											  + "ORDER BY seq ASC";
	
	public static final String UPDATE_DOWNLOAD_HIT = "update `JSP_FILE` set download=download+1 where parent=?";
	
}