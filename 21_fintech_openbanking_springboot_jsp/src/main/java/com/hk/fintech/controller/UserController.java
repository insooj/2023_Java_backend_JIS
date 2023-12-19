package com.hk.fintech.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hk.fintech.dtos.UserDto;
import com.hk.fintech.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	//회원가입폼 이동
	@GetMapping("/signup")
	public String signUp() {
		return "signup";
	}
	
	//사용자 인증을 완료하면 code들을 반환해준다.
	//반환받은 code를 이용해서 토큰 발급 요청을 진행한다.
	@GetMapping("/authresult")
	public String authResult(String code,Model model) throws IOException, ParseException {
		System.out.println("인증후 받은 code:"+code);
		
		HttpURLConnection conn=null;// api에서 제공하는 데이터를 받기 위한 연결 객체
		JSONObject result=null; //받아온 데이터를 json으로 저장할 객체
		
		//인증받고 얻은 code를 통해 토큰을 요청하여 발급받는다.
		URL url=new URL("https://testapi.openbanking.or.kr/oauth/2.0/token?"
				       +"code="+code
				       +"&client_id=4987e938-f84b-4e23-b0a2-3b15b00f4ffd"
				       +"&client_secret=3ff7570f-fdfb-4f9e-8f5a-7b549bf2adec"
				       +"&redirect_uri=http://localhost:8087/user/authresult"
				       +"&grant_type=authorization_code"
						);
		
		conn=(HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST"); //post로 요청 설정
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setDoOutput(true); //데이터를 가져온다면 true로 설정한다.
		
		// 데이터를 실제로 가져오는 작업
		BufferedReader br=new BufferedReader(
				         new InputStreamReader(conn.getInputStream(),"utf-8"));
		
		StringBuilder response=new StringBuilder();//데이터를 저장할 객체
		String responseLine=null;
		while((responseLine=br.readLine())!=null) {
//			System.out.println(responseLine);
			response.append(responseLine.trim());//데이터를 문자열에 추가
		}
		
		// json 형태의 텍스트 데이터를 json객체로 변환하는 작업
		result=(JSONObject)new JSONParser().parse(response.toString());
		
		//json객체에서 전달받은 값을 가져오기
		String access_token=result.get("access_token").toString();
		String refresh_token=result.get("refresh_token").toString();
		String user_seq_no=result.get("user_seq_no").toString();
		
		System.out.println("access_token:"+access_token);
		System.out.println("refresh_token:"+refresh_token);
		System.out.println("user_seq_no:"+user_seq_no);
		
		//클라이언트로 전달하기 위한  model저장
		model.addAttribute("access_token",access_token);
		model.addAttribute("refresh_token",refresh_token);
		model.addAttribute("user_seq_no",user_seq_no);
		
		return "authresult";
	}
	
	//회원가입
	@PostMapping("/adduser")
	public String addUser(UserDto dto) {
		boolean isS=userService.addUser(dto);
		
		if(isS) {
			System.out.println("회원가입성공");
			return "redirect:/";
		}else {
			System.out.println("회원가입실패");
			return "error";
		}
	}
	
	//로그인 폼 이동하기
	@GetMapping("/signin_form")
	public String signinForm() {
		System.out.println("로그인폼 이동");
		return "signin_form";
	}
	
	//로그인하기
	@PostMapping("/login")
	public String login(UserDto dto, HttpServletRequest request) {
		UserDto ldto=userService.loginUser(dto);
		if(ldto==null) {
			System.out.println("회원이 아님");
			return "redirect:/user/signin_form";
		}else {
			System.out.println("회원이 맞음");
			HttpSession session=request.getSession();
			session.setAttribute("ldto", ldto);//로그인 정보를 session에 저장
			session.setMaxInactiveInterval(60*10);
			return "redirect:/banking/main";
		}
	}
	
	//로그아웃하기
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		System.out.println("로그아웃");
		HttpSession session=request.getSession();
		session.invalidate();
		return "redirect:/";
	}
}











