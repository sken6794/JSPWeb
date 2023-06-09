package com.coding404.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.jasper.tagplugins.jstl.core.Out;

import com.coding404.user.model.UserVO;
import com.coding404.user.service.UserService;
import com.coding404.user.service.UserServiceImpl;

//1. 확장자패턴으로 변경한다.
@WebServlet("*.user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public UserController() {
		super();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	//2. get/post하나로 모은다
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//3.요청분기
		//한글 처리
		request.setCharacterEncoding("utf-8");

		String uri = request.getRequestURI();
		String conPath = request.getContextPath();

		String command = uri.substring(conPath.length());
		System.out.println(command);
		// MVC2 에서는 화면을 띄우는 요청도 컨트롤러를 거쳐 나가도록 처리
		// 기본이동이 전부 forward 형식으로 처리한다.
		
		//리다이렉트는 다시 컨트롤러를 타고 나가는 용도로 사용 
		
		//필요한 객체를 if문 바깥에 선언
		UserService service = new UserServiceImpl();
		
		//세션
		HttpSession session = request.getSession();
		
		if(command.equals("/user/user_join.user")) {
			request.getRequestDispatcher("user_join.jsp").forward(request, response);
		}else if(command.equals("/user/user_login.user")) {
			request.getRequestDispatcher("user_login.jsp").forward(request, response);
		}else if(command.equals("/user/joinForm.user")) {
			//가입 요청 처리
			
			//가입
			
			int result = service.join(request, response);
			
			if(result == 1) {
				request.setAttribute("msg", "중복된 아이디입니다.");
				request.getRequestDispatcher("user_join.jsp").forward(request, response);
			}else { //가입성공
				response.sendRedirect("user_login.user");
			}
		//로그인 기능
		} else if(command.equals("/user/loginForm.user")) {
			UserVO vo = service.login(request, response);
			if(vo==null) {//로그인 실패
				request.setAttribute("msg", "아이디 비밀번호를 확인하세요");
				request.getRequestDispatcher("user_login.jsp").forward(request, response);
			}else {//로그인 성공
				//세션에 회원정보 저장 (자바에서 세션 얻는 방법)
				 
				session.setAttribute("user_id", vo.getId());
				session.setAttribute("user_name", vo.getName());
				response.sendRedirect("user_mypage.user");
			}
		} else if(command.equals("/user/user_mypage.user")) {
			request.getRequestDispatcher("user_mypage.jsp").forward(request, response);
		//로그아웃 - 인증수단 삭제
		}else if(command.equals("/user/user_logout.user")) {
			
			session.invalidate();
			response.sendRedirect("user_login.user");
			//response.sendRedirect("../index.jsp");
		//정보 수정
		}else if(command.equals("/user/user_modify.user")) {
			
			//회원정보를 가지고 감
			UserVO vo = service.getInfo(request, response);
			request.setAttribute("vo", vo);
			request.getRequestDispatcher("user_modify.jsp").forward(request, response);
			
		} else if(command.equals("/user/user_update.user")) {
			
			int result = service.updateInfo(request, response);
			//3. 수정 성공시에는 mypage로 redirect, 실패시에는 modify로 redirect
			if(result == 1) { //성공
												
				//UserVO vo = service.getInfo(request, response);
				String name = request.getParameter("name");
				//session.setAttribute("user_id", vo.getId());
				//session.setAttribute("user_name", vo.getName());
				session.setAttribute("user_name", name);
				//session.setAttribute("vo", vo);
				
				//out객체를 이용한 메시지 전달
				response.setContentType("text/html; charset=utf-8;");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('회원정보 수정이 되었습니다.');");
				out.println("location.href='user_mypage.user';");
				out.println("</script>");
				
				
				//response.sendRedirect("user_mypage.user");
			}else { // 실패
				response.sendRedirect("user_modify.user");
			}
			
			
			
			
			
		}
		
	}

}
