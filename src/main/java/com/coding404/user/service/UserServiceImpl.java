package com.coding404.user.service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coding404.user.model.UserDAO;
import com.coding404.user.model.UserVO;

public class UserServiceImpl implements UserService{

	@Override
	public int join(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String gender = request.getParameter("gender");
		
		//아이디 중복검사
		//싱글톤 객체를 얻는 방법.
		UserDAO dao = UserDAO.getInstance();
		int result = dao.idCheck(id);
		
		if(result == 1){
			return 1;
		}else {
			UserVO vo = new UserVO(id, pw, name, email, gender, null);
			dao.join(vo);
			return 0;
		}
		
	}

	@Override
	public UserVO login(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		UserDAO dao = UserDAO.getInstance();
		UserVO vo = dao.login(id, pw);
		return vo;
	}

	@Override
	public UserVO getInfo(HttpServletRequest request, HttpServletResponse response) {
		
		//회원아이디 - 이전화면에서 필요한 값을 넘겨주는 방법 or 세션에 존재하는 회원아이디 받아오기
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("user_id");
		
		UserDAO dao = UserDAO.getInstance();
		UserVO vo = dao.getInfo(id);
		
		return vo;
	}

	@Override
	public int updateInfo(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * 서비스와 DAO영역을 작성
		 * 1. 아이디 기반으로 회원정보를 수정
		 * 2. 성공 실패 여부를 컨트롤러로 반환 받음.
		 * 3. 수정 성공시에는 mypage로 redirect, 실패시에는 modify로 redirect
		 */
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String gender = request.getParameter("gender");
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("user_id");
		UserVO vo = new UserVO(id, pw, name, email, gender, null);
		
		
		UserDAO dao = UserDAO.getInstance();
		int result = dao.updateInfo(vo);
		//성공 시 1, 실패 시 0
		return result;
	}

}


























