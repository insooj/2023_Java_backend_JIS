package com.hk.login.controller;

import java.util.List;
import java.util.Map;



import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hk.login.dtos.HkDto;
import com.hk.login.service.IHkService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HkController {
	
	@Autowired
	private IHkService hkService;
	
	@RequestMapping(value="/index.do",method=RequestMethod.GET)
	public String index(Model model ) {
		System.out.println("index.do 요청");
		return "redirect:index";
	}
	@RequestMapping(value="/registForm.do",method = RequestMethod.GET)
	public String registForm(Model model) {
		System.out.println("registForm.do 요청");
		return "board/registForm";
	}
	
	@RequestMapping(value="/login.do",method = RequestMethod.GET)
	public String login(Model model) {
		System.out.println("login.do요청");
		return "board/user_main";
	}
	@RequestMapping(value="/myinfo.do",method = RequestMethod.GET)
	public String myinfo(Model model, String Id ,HttpServletRequest request) {
		String ldto = hkService.getUserInfo(Id);
//		HttpSession session = request
		System.out.println("myinfo,do 요청");
		return "board/userInfo";
	}
	@RequestMapping(value="/home.do",method=RequestMethod.GET)
	public String home(Model model) {
		System.out.println("home.do요청");
		
		String msg="Spring 버전 게시판 만들기";
		model.addAttribute("msg", msg);
		
		return "home";
	}
	
	@RequestMapping(value="/boardList.do",method=RequestMethod.GET)
	public String boardList(Model model) {
		System.out.println("boardList.do요청");
		
		List<HkDto> list=hkService.getAllList();	
		model.addAttribute("list", list);
		
		return "board/boardList";// return "페이지이름"; --> viewResolver가 실행됨
	}
	
	@RequestMapping(value="/insertBoardForm.do",method=RequestMethod.GET)
	public String insertBoardForm(Model model) {
		
		System.out.println("insertBoardForm.do요청");
		
		return "board/insertBoardForm";// return "페이지이름"; --> viewResolver가 실행됨
	}
	
	@RequestMapping(value="/insertBoard.do",method=RequestMethod.POST)
	public String insertBoard(Model model,HkDto dto) {//파리미터 받는 방법(메서드에 파라미터로 선언)
//	public String insertBoard(Model model,String id, String titile) {
//	public String insertBoard(Model model,@RequestParam Map<String,String>map ) {
		System.out.println("insertBoard.do요청");
		boolean isS=hkService.insertBoard(dto);
		if(isS) {
			return "redirect:boardList.do";// "WEB-IN/views/boardList.do.jsp"->404
		}else {
			model.addAttribute("msg", "글추가실패");
			return "board/error";// return "페이지이름"; --> viewResolver가 실행됨			
		}
		
	}
	
	@RequestMapping(value="/detailBoard.do",method=RequestMethod.GET)
	public String detailBoard(Model model,int seq) {
//		String sseq=request.getParameter("seq");
		System.out.println("detailBoard.do요청");
		
		HkDto dto=hkService.getBoard(seq);
		
		model.addAttribute("dto", dto);
		return "board/detailBoard";// return "페이지이름"; --> viewResolver가 실행됨
	}
	
	@RequestMapping(value="/updateBoardForm.do",method=RequestMethod.GET)
	public String updateBoardForm(Model model,int seq) {

		System.out.println("updateBoardForm.do요청");
		
		HkDto dto=hkService.getBoard(seq);
		
		model.addAttribute("dto", dto);
		return "board/updateBoardForm";// return "페이지이름"; --> viewResolver가 실행됨
	}
	
	@RequestMapping(value="/updateBoard.do",method=RequestMethod.POST)
	public String updateBoard(HkDto dto,Model model) {

		System.out.println("updateBoard.do요청");
		
		boolean isS=hkService.updateBoard(dto);
		
		if(isS) {
			return "redirect:detailBoard.do?seq="+dto.getSeq();
		}else {
			model.addAttribute("msg", "수정실패");
			return "board/error";// return "페이지이름"; --> viewResolver가 실행됨			
		}
		
	}
	
	//method 전송방식 설정 : {PUT,POST,GET...}
	@RequestMapping(value="/mulDel.do",
	  method = {RequestMethod.PUT,RequestMethod.POST,RequestMethod.GET})
	public String mulDel(Model model,String[] chk) {

		System.out.println("mulDel.do요청");
		
		boolean isS=hkService.mulDel(chk);
		
		if(isS) {
			return "redirect:boardList.do";
		}else {
			model.addAttribute("msg", "삭제실패");
			return "board/error";// return "페이지이름"; --> viewResolver가 실행됨			
		}
		
	}
	@ResponseBody
	@RequestMapping(value="/idChk.do",method = {RequestMethod.POST})
	public String idChk (String id) {
		System.out.println("idChk.do");
		System.out.println(id);
		String rid = hkService.idChk(id);
		System.out.println(rid);
		if(rid == null) {
			return "null";
		}else {
			return "";
			
		}
	}
	
}