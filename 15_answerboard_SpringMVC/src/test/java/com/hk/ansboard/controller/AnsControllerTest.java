package com.hk.ansboard.controller;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeNoException;

import javax.servlet.http.Cookie;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
@WebAppConfiguration
public class AnsControllerTest {

	@Autowired
	public WebApplicationContext wac;
	public MockMvc mock;
	
	@Test()
	public void testGetAllList() throws Exception {
		// mock 객체를 활용해서 가상의 클라이언트 요청
		this.mock=MockMvcBuilders.webAppContextSetup(wac).build();
		
		//boardList.do를 요청해보자~~
		MvcResult result=mock.perform(MockMvcRequestBuilders.get("/boardList.do")
				                      .param("pnum", "1"))
				             .andExpect(MockMvcResultMatchers.status().isOk())
						     .andReturn();
		
		int statusCode=result.getResponse().getStatus();//결과내용을 받아옴
		System.out.println("status 코드:"+statusCode);
		System.out.println("HttpStatus:"+HttpStatus.OK.value());
		//200코드와 같은지를 비교하기---> 200은 성공, 404는 경로에러, 500은 코드 오류
		assertEquals(HttpStatus.OK.value(), statusCode);
	}

	//글 상세보기
	@Test
	public void testDetailBoard() throws Exception {
		// mock 객체를 활용해서 가상의 클라이언트 요청
		this.mock=MockMvcBuilders.webAppContextSetup(wac).build();
		
		//boardList.do를 요청해보자~~
		MvcResult result=mock.perform(MockMvcRequestBuilders.get("/detailBoard.do")
				                      .param("seq", "3"))
				             .andExpect(MockMvcResultMatchers.status().isOk())
						     .andReturn();
		
		int statusCode=result.getResponse().getStatus();//결과내용을 받아옴
		System.out.println("status 코드:"+statusCode);
		System.out.println("HttpStatus:"+HttpStatus.OK.value());
		//200코드와 같은지를 비교하기---> 200은 성공, 404는 경로에러, 500은 코드 오류
		assertEquals(HttpStatus.OK.value(), statusCode);
	}
	
	//글 추가 폼 이동
	@Test
	public void testInsertBoardForm() throws Exception {
		// mock 객체를 활용해서 가상의 클라이언트 요청
		this.mock=MockMvcBuilders.webAppContextSetup(wac).build();
		
		//boardList.do를 요청해보자~~
		MvcResult result=mock.perform(MockMvcRequestBuilders.get("/insertForm.do")
				                      )
				             .andExpect(MockMvcResultMatchers.status().isOk())
						     .andReturn();
		
		int statusCode=result.getResponse().getStatus();//결과내용을 받아옴
		System.out.println("status 코드:"+statusCode);
		System.out.println("HttpStatus:"+HttpStatus.OK.value());
		//200코드와 같은지를 비교하기---> 200은 성공, 404는 경로에러, 500은 코드 오류
		assertEquals(HttpStatus.OK.value(), statusCode);
	}
}








