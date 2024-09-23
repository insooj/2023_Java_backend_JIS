package com.hk.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//DispatcherServlet 객체가 @Controller로 선언된 클래스를 찾는다.
//"home.do"라고 요청했으면, 매핑되어 있는 메서드를 실행한다.
@RequestMapping(value="/board")
@Controller
public class HomeController {

//	@GetMapping
//	@PostMapping
//	@PutMapping
	@RequestMapping(value = "/home.do",method = RequestMethod.GET)
	public String home(Model model,HttpServletRequest request
								  ,HttpServletResponse response) {
		System.out.println("home 요청");
		String str="Hello Spring";
		
//		request.setAttribute("str",str);
		model.addAttribute("str", str);
		
		//우리가 사용하던 sendRedirect() 기능임
//		return "redirect:home.jsp?seq=5&id=hk";
		//우리가 사용하던 forward() 기능임
		return "home";//ViewResolver가 만들어줌:"WEB-INF/views/"+"home"+".jsp"
	}
	
	@RequestMapping(value = "/boardList.do",method = RequestMethod.GET)
	public String boardList() {
		System.out.println("글목록조회");
		return "home";
	}
}





