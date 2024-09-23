package com.hk.user.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpFilter;

//urlMapping하는 방법2가지 (XML, 어노테이션 )  여기서는 어노테이션방식을 사용했음
@WebFilter(urlPatterns = {"/*"},
		   initParams = {
				   @WebInitParam(name="encoding",value="utf-8")
		   }
		)
public class EncodeFilter extends HttpFilter implements Filter {

	private String encode;
	
	public void init(FilterConfig fConfig) throws ServletException {
		encode=fConfig.getInitParameter("encoding");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			    FilterChain chain) throws IOException, ServletException {
		
		//요청시 실행할 코드를 작성
		System.out.println("요청코드 실행");
		//인코딩 처리 코드 작성
		request.setCharacterEncoding(encode);
		response.setContentType("text/html;charset="+encode);
		
		chain.doFilter(request, response);
		
		//요청시 실행할 코드를 작성
		System.out.println("응답코드 실행");
	}

	public void destroy() {
		
	}

}
