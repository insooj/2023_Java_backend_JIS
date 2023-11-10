package com.hk.hello;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Driver;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


//servlet 객체: 클라이언트와 통신을 하기 위한 객체
//servlet 객체를 만들려면 HttpServlet클래스를 상속받아야 한다. 
public class HelloServletOld extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8821754557607055177L;

	@Override
	public void init() throws ServletException {
		System.out.println("init():servlet객체가 생성되면 최초 한번 실행 ");
	}
	
	//ServletConfig객체는 init()메서드의 파라미터로 얻어올수 있다. 
	@Override
	public void init(ServletConfig config) throws ServletException {
		//ServletContext 객체는 ServletConfig객체로부터 얻어 올 수 있다. 
		
		
		String name = config.getInitParameter("name");
		System.out.println("config를해서 값 가져오기:" + name);
		
		ServletContext application = config.getServletContext();
		
		application.setAttribute("id", "id");//객체 저장 
		String id =(String)application.getAttribute("id");
		
		String driver = application.getInitParameter("driver");
		System.out.println("application을 통해 값 가져오기:"+ driver);
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("get방식 ");
		
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		request.setCharacterEncoding("utf-8");
//		response.setContentType("text/html;charset=utf-8");
		//Session 객체 구하기 
		HttpSession session=request.getSession();
//		UserDto ldto = (UserDto)session.getAttribute("ldto");
		
		//파라미터 받기
		String param = request.getParameter("param");
		
		
		//java의 결과를 HTML에 출력하기 
		PrintWriter out = response.getWriter();
		out.print("<h1>서블릿 응답</h1>");
		out.print("<h2>서블릿 3대개념 알아보기</h2>");
		out.print("<h3><a href='index.jsp'>index</a></h3>");
		
		//서블릿에서 바로 다른 페이지 응답 
		//resp.sendRedirect("test.jsp");
//		request.getRequestDispatcher("test.jsp").forward(request, response);
		test(request);//다른 객체에서 요청정보를 처리하려면 request객체를 전달해야 함 
	}
	private void test(HttpServletRequest request) {

//		HttpServletRequest request = new HttpServletRequest();
		request.getSession(); //session객체 생성 
	
	}
	
	
	@Override
	public void destroy() {
		System.out.println("destroy(): 더이상요청이 없으면 서블릿 소멸 ");
	}
}
