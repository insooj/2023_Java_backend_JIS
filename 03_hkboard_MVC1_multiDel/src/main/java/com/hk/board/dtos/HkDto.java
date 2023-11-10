package com.hk.board.dtos;

import java.io.Serializable;
import java.util.Date;


//DTO객체: 데이터를 저장하고 운반하기 위한 객체 
//데이터를 담는 용도이기 때문에 맴버필드는 은닉화로 관리함. 
public class HkDto implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//Serializable 인터페이스를 구현 - > 직렬화 data [ d a t a ] 하여 전송하는 방법 
//데이터를 안정적으로 관리 

	
	private int seq;
	public HkDto(int seq, String id, String title, String content, Date regDate) {
		super();
		this.seq = seq;
		this.id = id;
		this.title = title;
		this.content = content;
		this.regDate = regDate;
	}
	
	public HkDto(int seq, String title, String content) {
		super();
		this.seq = seq;
		this.title = title;
		this.content = content;
	}
	
	public HkDto(String id, String title, String content) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
	}


	private String id;
	private String title;
	private String content;
	private Date regDate; 
	
	public HkDto() {
		super();
	}		

	@Override
	public String toString() {
		return "HkDto [seq=" + seq + ", id=" + id + ", title=" + title + ", content=" + content + ", regDate=" + regDate
				+ "]";
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

}
