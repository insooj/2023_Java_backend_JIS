package com.hk.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.CoderMalfunctionError;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hk.board.daos.HkDao;
import com.hk.board.dtos.HkDto;

@WebServlet("*.board")
public class HkController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//인코딩 처리는 filter로 구현했음
		
		//command 구현하기
		String requestURI=request.getRequestURI();
		String contextPath=request.getContextPath();
		String pathInfo=request.getPathInfo();
		StringBuffer requestURL=request.getRequestURL();
		
		//글목록요청    url: boardList.board 
		//글상세보기요청 url: detailBoard.board
		System.out.println(requestURI+"\n"
						  +contextPath+"\n"
						  +pathInfo+"\n"
						  +requestURL.toString());
		
		//1단계
		String command=requestURI.substring(contextPath.length());
		System.out.println("command값:"+command);
		
		//2단계
		HkDao dao=new HkDao();
		
		//3단계 command값 분기
		if(command.equals("/boardList.board")) {//글목록 보여주기
			List<HkDto> list=dao.getAllList();
			request.setAttribute("list", list);
			
			//forward()구현
			dispatch("board/boardList.jsp", request, response);
		}else if(command.equals("/insertBoardForm.board")){//글추가폼이동
 //         sendRedirect로 이동하면 경로가 남는다. /board/*
//			response.sendRedirect("board/insertBoardForm.jsp");
			
			//forward방식으로 응답하자..   *.jsp로 응답할 경우
			dispatch("board/insertBoardForm.jsp", request, response);
			
		}else if(command.equals("/insertBoard.board")) {
			String id=request.getParameter("id");
			String title=request.getParameter("title");
			String content=request.getParameter("content");
			
			boolean isS=dao.insertBoard(new HkDto(id,title,content));
			
			//html출력용 프린터 구현
			PrintWriter out=response.getWriter();
			String str="";
			if(isS) {
				    str="<script type='text/javascript'>" 
					+   "alert('글을 추가합니다.');"
					+   "location.href='boardList.board';"
					+   "</script>";	
			}else {
			        str="<script type='text/javascript'>" 
					+   "alert('글 삭제 실패.');"
					+   "location.href='error.board?msg=글추가실패';"
					+   "</script>";
			}
			out.print(str);
		}else if(command.equals("/error.board")) {//오류페이지 이동
			dispatch("board/error.jsp", request, response);
		}else if(command.equals("/detailBoard.board")) {//글상세조회
			int seq=Integer.parseInt(request.getParameter("seq"));
			HkDto dto=dao.getBoard(seq);
			
			request.setAttribute("dto", dto);
			dispatch("board/detailBoard.jsp", request, response);
		}else if(command.equals("/updateBoardForm.board")) {//수정폼이동
			int seq=Integer.parseInt(request.getParameter("seq"));
			HkDto dto=dao.getBoard(seq);
			request.setAttribute("dto", dto);
			dispatch("board/updateBoardForm.jsp", request, response);
		}else if(command.equals("/updateBoard.board")) {
			int seq=Integer.parseInt(request.getParameter("seq"));
			String title=request.getParameter("title");
			String content=request.getParameter("content");
			
			boolean isS=dao.updateBoard(new HkDto(seq,title,content));
			//html출력용 프린터 구현
			PrintWriter out=response.getWriter();
			String str="";
			if(isS) {
				    str="<script type='text/javascript'>" 
					+   "alert('글 수정완료');"
					+   "location.href='detailBoard.board?seq="+seq+"';"
					+   "</script>";	
			}else {
			       str=jsForward("글수정실패","error.board?msg=글수정실패");
			}
			out.print(str);
		}else if(command.equals("/deleteBoard.board")) {
			int seq=Integer.parseInt(request.getParameter("seq"));
			boolean isS=dao.deleteBoard(seq);
			
			PrintWriter out=response.getWriter();
			String str="";
			if(isS) {
				str=jsForward("글삭제성공", "boardList.board");
			}else {
				str=jsForward("글삭제실패", "error.board?msg=글삭제실패");
			}
			out.print(str);
		}else if(command.equals("/muldel.board")) {
			String [] chks=request.getParameterValues("chk");
			boolean isS=dao.mulDel(chks);
			
			PrintWriter out=response.getWriter();
			String str="";
			if(isS) {
				str=jsForward("글삭제성공", "boardList.board");
			}else {
				str=jsForward("글삭제실패", "error.board?msg=글삭제실패");
			}
			out.print(str);
		}
		
	}
	
	public String jsForward(String msg,String url) {
		String str="<script type='text/javascript'>" 
				+   "alert('"+msg+"');"
				+   "location.href='"+url+"';"
				+   "</script>";
		return str;
	}
	
	//forward구현
	public void dispatch(String url, HttpServletRequest request, 
						 HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(url).forward(request, response);
	}

}










