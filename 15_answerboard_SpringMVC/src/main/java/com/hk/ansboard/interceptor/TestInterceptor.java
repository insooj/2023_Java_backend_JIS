package com.hk.ansboard.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

//해당 클래스를 인터셉터로 설정하려면 HandlerInterceptor를 구현한다.
public class TestInterceptor implements HandlerInterceptor{

	Logger logger=LoggerFactory.getLogger(getClass());
	
	//컨트롤러 실행 전에 호출된다.
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session =request.getSession();
		Object obj=session.getAttribute("ldto");//로그인 정보
		
		//요청 url로 구별해서 처리
		if(request.getRequestURI().contains("/boardList.do")) {
			logger.info(request.getRequestURI()+"요청함");
			System.out.println("글목록 요청했습니다.");
			return true;
		}else if(request.getRequestURI().contains("/detailBoard.do")) {
			if(obj==null) {
				System.out.println("글상세조회는 로그인 한 후에 조회 가능");
				logger.info(request.getRequestURI()+"글상세조회");
//				response.sendRedirect("index.jsp");
			}
		}
		
		return true;
	}
	
	//컨트롤러를 실행 후 DispatcherServlet이 뷰로 보내기 전에 호출됨
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("인터셉터:postHandle실행");
	}
	
	//컨트롤러에서 뷰까지 실해이 완료된 후 호출됨
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("인터셉터:afterCompletion실행");
	}
}





