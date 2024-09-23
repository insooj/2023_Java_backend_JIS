package com.hk.ansboard.service;

import java.util.List;

import com.hk.ansboard.dtos.AnsDto;

public interface IAnsService {
	//1.글목록조회[페이징처리]
	public List<AnsDto> getAllList(String pnum);
	//1-2.페이지수 구하기
	public int getPCount() ;
	//2.새글추가하기
	public boolean insertBoard(AnsDto dto) ;
	//3.상세조회
	public AnsDto getBoard(int seq) ;
	//4.수정하기
	public boolean updateBoard(AnsDto dto) ;
	//5.조회수
	public boolean readCount(int seq) ;
	//6.삭제하기
	public boolean mulDel(String[] seqs) ;
	//7.답글달기
	public boolean replyBoard(AnsDto dto);
}



