package com.hk.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hk.board.command.DelBoardCommand;
import com.hk.board.dtos.BoardDto;
import com.hk.board.service.BoardService;

@Controller
@RequestMapping(value="/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	@GetMapping(value="/boardList")
	public String boardList(Model model) {
		System.out.println("글목록 보기");
		List<BoardDto> list= boardService.getAllList();
		model.addAttribute("list",list);
		model.addAttribute("delBoardCommand", new DelBoardCommand());
		return "board/boardList"; // forward기능 ,"redirect: board/boardList"
	}
	
}