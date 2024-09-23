package com.hk.ansboard;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hk.ansboard.dtos.AnsDto;
import com.hk.ansboard.service.IAnsService;
import com.hk.ansboard.utils.Paging;

@Controller
//@RequestMapping(value = "/board")
public class AnsController {

	@Autowired
	private IAnsService ansService;
	
	//getCookie 기능 구현
	public Cookie getCookie(String cookieName, HttpServletRequest request) {
		Cookie[] cookies=request.getCookies();
		Cookie cookie=null;
		if(cookies!=null) {
			for (int i = 0; i < cookies.length; i++) {
				if(cookies[i].getName().equals(cookieName)) {
					cookie=cookies[i];
				}
			}			
		}
		return cookie;
	}
	
	@RequestMapping(value = "/boardList.do", method = RequestMethod.GET)
	public String getAllList(String pnum,Model model
			,HttpServletRequest request
			,HttpServletResponse response) {
		//글목록으로 이동하면 쿠키 rseq값을 삭제하자
		Cookie cookie=getCookie("rseq", request);
		if(cookie!=null) {
			cookie.setMaxAge(0);//유효기간 0 --> 삭제됨
			response.addCookie(cookie);//클라이언트로 변경사항을 전달
		}
		//쿠키 삭제 코드 종료------------
		
		//---페이지번호 유지를 위한 코드----------------------
		//페이지번호를 전달하지 않으면 세션에 저장된 페이지번호를 사용
		HttpSession session=request.getSession();
		if(pnum==null) {
			pnum=(String)session.getAttribute("pnum");//현재 조회중인 페이지번호
		}else {
			//새로 페이지를 요청할 경우 세션에 저장
			session.setAttribute("pnum", pnum);
		}
		//---페이지번호 유지를 위한 코드 종료-------------------
		
		//글목록 구하기
		List<AnsDto>list=ansService.getAllList(pnum);
		model.addAttribute("list", list);
		
		//페이지 수 구하기 
		int pcount=ansService.getPCount();
		model.addAttribute("pcount", pcount);
		
		//---페이지에 페이징 처리 기능 추가
		//필요한 값: 페이지수, 페이지번호, 페이지범위(페이지수)
		Map<String, Integer>map=Paging.pagingValue(pcount, pnum, 10);
		model.addAttribute("pMap", map);
		
		return "board/boardList";//"WEB-INF/views/"+boardList+".jsp"
	}
	
	@RequestMapping(value = "/detailBoard.do",method = RequestMethod.GET)
	public String detailBoard(int seq,Model model
							,HttpServletRequest request
							,HttpServletResponse response) {
		
		//상세내용 조회
		AnsDto dto=ansService.getBoard(seq);
		model.addAttribute("dto", dto);
		
		String s=null;
		
		//getCookie메서드 구현해서 활용하기
		Cookie cookieObj=getCookie("rseq", request);
		
		if(cookieObj!=null) {//cookie가 null이 아닐 경우 실행
			s=cookieObj.getValue();				
		}
		
		//"rseq"라는 이름의 값이 있는지 확인(쿠키값이 없는 경우)
		if(s==null||!s.equals(String.valueOf(seq))) {
			//쿠키객체 생성하기
			//                    cookie에 값을 저장할때 타입은 String 이다
			Cookie cookie=new Cookie("rseq", String.valueOf(seq));
			cookie.setMaxAge(60*10);//유효기간 설정
			response.addCookie(cookie);//클라이언트로 cookie객체 전달
			
			//---조회수 올리기 코드
			ansService.readCount(seq);//조회수 증가
			//--조회수 코드 종료
		}
		
		return "board/detailBoard";
	}
	
	@RequestMapping(value = "/insertForm.do",method = RequestMethod.GET)
	public String insertBoardForm() {
		return "board/insertForm";
	}
	
	@RequestMapping(value = "/insertBoard.do",method = RequestMethod.POST)
	public String insertBoard(Model model, AnsDto dto) { // param=id, title, content -> AnsDto로 받음 
	
		boolean isS = ansService.insertBoard(dto);
	
		if(isS) {
//			return "boardList"; // WEB-INF/views/boardList.jsp --> 오류500
			return "redirect:boardList.do";
		}else {
			model.addAttribute("msg", "글추가 실패");
			return "error";
		}

	}
	
	@RequestMapping(value = "/updateBoardForm.do",method = RequestMethod.GET)
	public String updateBoardForm(Model model, @RequestParam(value="seq")int seq) {
		AnsDto dto=ansService.getBoard(seq);
		model.addAttribute("dto", dto);
		return "board/updateBoard";
	}
	
	@RequestMapping(value = "/updateBoard.do",method = RequestMethod.POST)
	public String updateBoard(Model model, AnsDto dto) {
		boolean isS=ansService.updateBoard(dto);
		
		if(isS) {
			return "redirect:detailBoard.do?seq="+dto.getSeq();
		}else {
			model.addAttribute("msg", "수정실패");
			return "error";
		}
	
	}
	
	@RequestMapping(value = "/mulDel.do",method = {RequestMethod.POST
			                                      ,RequestMethod.GET})
	public String mulDel(Model model, @RequestParam String[] chk) {
		boolean isS=ansService.mulDel(chk);
		
		if(isS) {
			return "redirect:boardList.do";
		}else {
			model.addAttribute("msg", "삭제실패");
			return "error";
		}
	
	}
	
	@RequestMapping(value = "/replyBoard.do",method = {RequestMethod.POST
            								,RequestMethod.GET})
	public String replyBoard(Model model, AnsDto dto) {
		
		boolean isS=ansService.replyBoard(dto);
		
		if(isS) {
			return "redirect:boardList.do";
		}else {
			model.addAttribute("msg", "답글추가실패");
			return "error";
		}
	
	}
	
	//@ResponseBody : 요청했던 페이지로 값을 출력하는 기능
	//servlet에서 사용하던 printWriter pw=response.getWriter(); pw.print("<p>t</p>")
	@ResponseBody
	@RequestMapping(value="/detailBoardAjax.do",method = RequestMethod.POST
			        ,produces = "application/json;charset=utf-8")
	public Map<String, AnsDto> detailBoardAjax(int seq){
		AnsDto dto=ansService.getBoard(seq);
		
		//날짜를 원하는 형식의 문자열으로 변환 처리
		Date regDate=dto.getRegDate();//Date타입 값 가져오기
		SimpleDateFormat sf=new SimpleDateFormat("yyyy년MM월dd일");
		String regDateStr=sf.format(regDate);
		dto.setRegDateStr(regDateStr);//문자열 타입 값 저장
		
		//JS: json객체사용--> {key:value,key:value,key:value}
		Map<String, AnsDto> map=new HashMap<>();
		map.put("dto", dto);// {"dto":{seq:5,id:"hk",title:"제목"..}}
		return map;
	}
	
	@GetMapping(value = "/home.do")
	public String home() {
		System.out.println("HOME요청");
		return "home";
	}
}





