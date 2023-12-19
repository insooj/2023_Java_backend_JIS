package com.hk.fintech.dtos;

public class UserDto {
	
	private String username;
	private String useremail;
	private String userpassword;
	
	private String useraccesstoken; // 접근을 위한 토큰
	private String userrefreshtoken;// 갱신을 위한 토큰
	private int userseqno; // 사용자일련번호
	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserDto(String username, String useremail, String userpassword, String useraccesstoken,
			String userrefreshtoken, int userseqno) {
		super();
		this.username = username;
		this.useremail = useremail;
		this.userpassword = userpassword;
		this.useraccesstoken = useraccesstoken;
		this.userrefreshtoken = userrefreshtoken;
		this.userseqno = userseqno;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUseremail() {
		return useremail;
	}
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
	public String getUserpassword() {
		return userpassword;
	}
	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}
	public String getUseraccesstoken() {
		return useraccesstoken;
	}
	public void setUseraccesstoken(String useraccesstoken) {
		this.useraccesstoken = useraccesstoken;
	}
	public String getUserrefreshtoken() {
		return userrefreshtoken;
	}
	public void setUserrefreshtoken(String userrefreshtoken) {
		this.userrefreshtoken = userrefreshtoken;
	}
	public int getUserseqno() {
		return userseqno;
	}
	public void setUserseqno(int userseqno) {
		this.userseqno = userseqno;
	}
	@Override
	public String toString() {
		return "UserDto [username=" + username + ", useremail=" + useremail + ", userpassword=" + userpassword
				+ ", useraccesstoken=" + useraccesstoken + ", userrefreshtoken=" + userrefreshtoken + ", userseqno="
				+ userseqno + "]";
	}
	
}
