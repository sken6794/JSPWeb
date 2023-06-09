package com.coding404.user.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UserDAO {
	
	//싱글톤 형태의 클래스로 생성하는편이 좋다.
	// 1. 나 자신의 객체를 스태틱으로 선언
	private static UserDAO instance = new UserDAO();
	
	//2. 직접 생성하지 못하도록 생성자 제한
	private UserDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//3. getter를 통해서 객체를 반환
	public static UserDAO getInstance() {
		return instance;
	}
	
	//데이터베이스 연결 주소
	//오라클 커넥터
	private String url ="jdbc:oracle:thin:@localhost:1521:xe";
	private String uid = "JSP";
	private String upw = "JSP"; 
			
	//중복검사
	public int idCheck(String id) {
		int result = 1;
		String sql = "select * from users where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		
		try {
			
			conn = DriverManager.getConnection(url, uid, upw);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) { //중복 o
				result = 1;
			} else { //중복 x
				result = 0;
			}
		} catch (Exception e) {
			System.out.println("idCheck 메소드 오류");
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				pstmt.close();
				rs.close();
			} catch (Exception e2) {
			}
		}
		return result;
	}
	
	//회원가입
	public void join(UserVO vo) {
		
		String sql = "insert into users(id,pw,name,email,gender) values (?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DriverManager.getConnection(url, uid, upw);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getEmail());
			pstmt.setString(5, vo.getGender());
			
			pstmt.executeUpdate(); //성공시 1, 실패 0
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	//로그인 기능
	public UserVO login(String id, String pw) {
		//로그인 성공이면 객체가 반환, 실패하면 null값이 반환
		UserVO vo = null;
		String sql = "select * from users where id=? and pw =?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(url, uid, upw);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String userid = rs.getString("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String gender = rs.getString("gender");
				Timestamp regdate = rs.getTimestamp("regdate");
				
				vo = new UserVO(userid, null, name, email, gender, regdate);
				
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				pstmt.close();
				rs.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return vo;
	}
	
	//회원정보 조회
	public UserVO getInfo(String id) {
		//로그인 성공이면 객체가 반환, 실패하면 null값이 반환
		UserVO vo = null;
		String sql = "select * from users where id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(url, uid, upw);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String userid = rs.getString("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String gender = rs.getString("gender");
				
				vo = new UserVO(userid, null, name, email, gender, null);
				
			} 
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("getInfo의 메소드 오류");
		}finally {
			try {
				conn.close();
				pstmt.close();
				rs.close();
			} catch (Exception e2) {
				
			}
		}
		return vo;
	}
	
	//회원정보 수정
	public int updateInfo(UserVO vo) {
		//로그인 성공이면 객체가 반환, 실패하면 null값이 반환
		int result = 0;
		String sql = "update users set pw = ?,name = ?, email = ?, gender= ? where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DriverManager.getConnection(url, uid, upw);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getPw());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getEmail());
			pstmt.setString(4, vo.getGender());
			pstmt.setString(5, vo.getId());
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("getInfo의 메소드 오류");
		}finally {
			try {
				conn.close();
				pstmt.close();
			} catch (Exception e2) {
				
			}
		}
		return result;
	}
	
}














