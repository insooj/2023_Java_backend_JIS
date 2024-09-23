package com.hk.ansboard.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hk.ansboard.AnsController;

//@Controller어노테이션이 적용된 곳에서 발생되는 예외를 catch한다.
@ControllerAdvice
public class ExceptionAdvice {

	private Logger logger=LoggerFactory.getLogger(getClass());
	
	//실행중에 오류가 발생하면 실행되는 메서드
	@ExceptionHandler(Exception.class)
	public String handleException(Exception e, Model model) {
		logger.error("Exception 발생:{}",e.getMessage());
		model.addAttribute("msg","오류가 발생하여 확인중");
		return "error";
	}
	
} 












