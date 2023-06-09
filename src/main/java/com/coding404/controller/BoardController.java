package com.coding404.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coding404.board.model.BoardVO;
import com.coding404.board.service.BoardService;
import com.coding404.board.service.BoardServiceImpl;

@WebServlet("*.board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String uri = request.getRequestURI();
		String conPath = request.getContextPath();
		
		String command = uri.substring(conPath.length());
		System.out.println(command);
		
		BoardService service = new BoardServiceImpl();
		
		//글쓰기 화면 처리
		if(command.equals("/board/board_write.board")) {
			
			
			request.getRequestDispatcher("board_write.jsp").forward(request, response);
		//목록 화면 처리
		}else if(command.equals("/board/board_list.board")) {
			
			//목록을 가지고 나옴
			List<BoardVO> list = service.getList(request, response);
			request.setAttribute("list", list);
			request.getRequestDispatcher("board_list.jsp").forward(request, response);
		//등록 작업
		}else if(command.equals("/board/registForm.board")) {
			service.resist(request, response);
			
			response.sendRedirect("board_list.board");
		//글 내용 보기
		}else if(command.equals("/board/board_content.board")) {
			BoardVO vo = service.getContent(request, response);
			request.setAttribute("vo", vo);
			request.getRequestDispatcher("board_content.jsp").forward(request, response);
		//수정 화면
		}else if(command.equals("/board/board_modify.board")) {
			//getContent() : bno 필요 
			BoardVO vo = service.getContent(request, response);
			request.setAttribute("vo", vo);
			
			
			request.getRequestDispatcher("board_modify.jsp").forward(request, response);
		//글 수정
		}else if(command.equals("/board/board_update.board")) {
			
			service.update(request, response);
			response.sendRedirect("board_content.board?bno="+request.getParameter("bno"));
			
		}
	}
}
