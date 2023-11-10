package com.hk.login.service;

import java.util.List;

import com.hk.login.dtos.HkDto;

public interface IHkService {

	//글목록 조회
	public List<HkDto> getAllList();
	//글 추가하기
	public boolean insertBoard(HkDto dto);
	//글 상세조회
	public HkDto getBoard(int seq);
	//글 수정하기
	public boolean updateBoard(HkDto dto);
	//글 삭제하기
	public boolean mulDel(String[] seqs);
	
	public String idChk(String id);
	
	public String getUserInfo(String id);
}


