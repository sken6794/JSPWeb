package com.coding404.board.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {
	//싱글톤 형태의 클래스로 생성하는편이 좋다.
	// 1. 나 자신의 객체를 스태틱으로 선언
	private static BoardDAO instance = new BoardDAO();

	//2. 직접 생성하지 못하도록 생성자 제한
	private BoardDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//3. getter를 통해서 객체를 반환
	public static BoardDAO getInstance() {
		return instance;
	}

	//데이터베이스 연결 주소
	//오라클 커넥터
	private String url ="jdbc:oracle:thin:@localhost:1521:xe";
	private String uid = "JSP";
	private String upw = "JSP"; 

	//글 등록
	public void regist(String writer, String title, String content) {

		String sql = "INSERT INTO BOARD (BNO, WRITER, TITLE, CONTENT) VALUES (BOARD_SEQ.NEXTVAL, ?,?,?)";

		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;

		try {
			conn = DriverManager.getConnection(url, uid, upw);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, writer);
			pstmt.setString(2, title);
			pstmt.setString(3, content);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("regist메소드 오류");
		} finally {
			try {
				conn.close();
				pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}


	}
	
	//글 목록 불러오기 
	public List<BoardVO> getList() {

		String sql = "select * from board order by bno desc";
		List<BoardVO> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(url, uid, upw);
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				BoardVO vo = new BoardVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getTimestamp(6));
				list.add(vo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("getList메소드 오류");
		} finally {
			try {
				conn.close();
				pstmt.close();
				rs.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return list;
	}
	
	//글 내용 조회
	public BoardVO getContent(int bno) {

		String sql = "select * from board where bno=?";
		BoardVO vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		
		try {
			conn = DriverManager.getConnection(url, uid, upw);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo = new BoardVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getTimestamp(6));
						
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("getContent메소드 오류");
		} finally {
			try {
				conn.close();
				pstmt.close();
				rs.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return vo;
	}
	
	//글 수정기능
	public void update(String bno, String title, String content) {
		
		String sql = "update board set title=?, content=? where bno =?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DriverManager.getConnection(url, uid, upw);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, bno);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("update메소드 오류");
		} finally {
			try {
				conn.close();
				pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		
	}
}







