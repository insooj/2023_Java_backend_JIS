package com.hk.user.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hk.user.daos.UserDao;
import com.hk.user.dtos.UserDto;

//url mapping : 클라이언트에서 ~.user라고 요청하면 해당 서블릿이 실행된다.
@WebServlet("*.user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//요청을 식별하기위해 url로부터 command값을 추출하기 위해 구해준다.
		String requestURI=request.getRequestURI();
		String contextPath=request.getContextPath();
		
		String command=requestURI.substring(contextPath.length());
		System.out.println("command값:"+command);
		
		//DB 쿼리 작성: DAO 객체 구하기
		UserDao dao=UserDao.getUserDao();//싱글톤패턴 적용
		
		//로그인관리:session객체 구하기
		HttpSession session=request.getSession();
		
		if(command.equals("/registForm.user")) {//회원가입폼이동
			dispatch("registForm.jsp", request, response);
		}else if(command.equals("/home.user")) {//index(로그인화면)이동
			dispatch("index.jsp", request, response);
		}else if(command.equals("/addUser.user")) {
			//회원가입 폼에서 입력한 정보를 받기
			String id=request.getParameter("id");
			String name=request.getParameter("name");
			String password=request.getParameter("password");
			String address=request.getParameter("address");
			String email=request.getParameter("email");
			
			boolean isS=dao.insertUser(new UserDto(id,name,password,address,email));
			if(isS) {
				jsForward("회원가입성공", "home.user", response);
			}else {
				jsForward("회원가입실패", "registForm.user", response);
			}
		}else if(command.equals("/login.user")) {
			String id=request.getParameter("id");
			String password=request.getParameter("password"); 
			
			UserDto ldto=dao.getLogin(id, password);
			if(ldto==null||ldto.getId()==null) {
				request.setAttribute("msg", "회원이 아닙니다. 가입해주세요");
				dispatch("index.jsp", request, response);
			}else {
				//회원이면 session객체에 회원정보를 저장
				session.setAttribute("ldto", ldto);
				session.setMaxInactiveInterval(10*60);
				
				//회원에 등급에 따라 메인 페이지 이동
				if(ldto.getRole().toUpperCase().equals("ADMIN")) {
					dispatch("admin_main.jsp", request, response);
				}else if(ldto.getRole().toUpperCase().equals("MANAGER")) {
					dispatch("manager_main.jsp", request, response);
				}else if(ldto.getRole().toUpperCase().equals("USER")) {
					dispatch("user_main.jsp", request, response);
				}
			}
		}else if(command.equals("/logout.user")) {//로그아웃
			session.invalidate();
			response.sendRedirect("home.user");
		}else if(command.equals("/myinfo.user")) {//나의정보조회
			String id=request.getParameter("id");
			UserDto dto=dao.getUserInfo(id);
			request.setAttribute("dto", dto);
			dispatch("userInfo.jsp", request, response);
		}else if(command.equals("/updateUser.user")) {//나의 정보 수정
			String id=request.getParameter("id");
			String address=request.getParameter("address");
			String email=request.getParameter("email");
			
			boolean isS=dao.updateUser(new UserDto(id,address,email));
			if(isS) {
				//        /path: 상위root에서, ../path:현재위치에서 한단계 위 ,
				//       ./path:현재경로에서  ,    path: 왼쪽과 동일
				jsForward("수정완료", "myinfo.user?id="+id, response);
			}else {
				jsForward("수정실패", "error.jsp?msg=수정실패",response);
			}
		}else if(command.equals("/delUser.user")) {//탈퇴
			String id=request.getParameter("id");
			boolean isS=dao.delUser(id);
			if(isS) {
				jsForward("탈퇴합니다.~~ ", "home.user", response);
			}else {
				jsForward("탈퇴실패", "error.jsp?msg=탈퇴실패", response);
			}
		}else if(command.equals("/idChk.user")) {
			String id=request.getParameter("id");
			System.out.println("id:"+id);
			
			//id가 DB에 존재하는지 여부를 조회
			String resultId=dao.idCheck(id);
			
			//java의 결과값(text)를 브라우저로 출력시키면 
			//$.ajax()가 받는다.
			PrintWriter out=response.getWriter();
			out.print(resultId);
			
			// java  ---> JS에서 사용할 수 있는 객체로 변환해야 한다.
			// 변환하려면 특정 라이브러리가 필요함 -> json.jar...
			// java --> json과 비슷한 자료 구조 --> Map (key:value)
			// json객체 전달: {"list":list}, {"dto":dto}
			
		}
		
	}
	
	//forward구현
	public void dispatch(String url, HttpServletRequest request, 
						 HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(url).forward(request, response);
	}

	//javascript로 응답 구현
	public void jsForward(String msg,String url
		,HttpServletResponse response) throws IOException {
		
		PrintWriter out=response.getWriter();
		String str="<script type='text/javascript'>" 
				+   "alert('"+msg+"');"
				+   "location.href='"+url+"';"
				+   "</script>";
		out.print(str);
		
	}
}









