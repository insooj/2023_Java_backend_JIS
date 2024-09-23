package com.hk.dtos;

import java.util.Date;

public class FileDto {

	private int seq;
	private String origin_name;
	private String stored_name;
	private int file_size;
	private Date f_regdate;
	public FileDto(int seq, String origin_name, String stored_name, int file_size, Date f_regdate) {
		super();
		this.seq = seq;
		this.origin_name = origin_name;
		this.stored_name = stored_name;
		this.file_size = file_size;
		this.f_regdate = f_regdate;
	}
	@Override
	public String toString() {
		return "FileDto [seq=" + seq + ", origin_name=" + origin_name + ", stored_name=" + stored_name + ", file_size="
				+ file_size + ", f_regdate=" + f_regdate + "]";
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getOrigin_name() {
		return origin_name;
	}
	public void setOrigin_name(String origin_name) {
		this.origin_name = origin_name;
	}
	public String getStored_name() {
		return stored_name;
	}
	public void setStored_name(String stored_name) {
		this.stored_name = stored_name;
	}
	public int getFile_size() {
		return file_size;
	}
	public void setFile_size(int file_size) {
		this.file_size = file_size;
	}
	public Date getF_regdate() {
		return f_regdate;
	}
	public void setF_regdate(Date f_regdate) {
		this.f_regdate = f_regdate;
	}
	public FileDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

	

}
