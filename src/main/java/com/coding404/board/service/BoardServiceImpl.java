package com.coding404.board.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coding404.board.model.BoardDAO;
import com.coding404.board.model.BoardVO;

public class BoardServiceImpl implements BoardService{

	@Override
	public void resist(HttpServletRequest request, HttpServletResponse response) {
		
		//title, writer, content
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		
		BoardDAO dao = BoardDAO.getInstance();
		dao.regist(writer, title, content);
	}

	@Override
	public List<BoardVO> getList(HttpServletRequest request, HttpServletResponse response) {
		List<BoardVO> list = new ArrayList<>();
		BoardDAO dao = BoardDAO.getInstance();
		list = dao.getList();
		
		return list;
	}

	@Override
	public BoardVO getContent(HttpServletRequest request, HttpServletResponse response) {
		
		BoardDAO dao = BoardDAO.getInstance();
		int bno = Integer.parseInt(request.getParameter("bno"));
		BoardVO vo =  dao.getContent(bno);
		
		
		
		return vo;
	}

	@Override
	public void update(HttpServletRequest request, HttpServletResponse response) {
		String bno = request.getParameter("bno");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		BoardDAO dao = BoardDAO.getInstance();
		dao.update(bno, title, content);
	}

}
