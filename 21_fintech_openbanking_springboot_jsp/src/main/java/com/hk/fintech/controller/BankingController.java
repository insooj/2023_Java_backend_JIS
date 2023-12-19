package com.hk.fintech.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hk.fintech.apidto.UserMeDto;
import com.hk.fintech.dtos.UserDto;
import com.hk.fintech.feignMapper.OpenBankingFeign;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/banking")
public class BankingController {
	
	@Autowired
	private OpenBankingFeign openBankingFeign;
	
	@GetMapping("/main")
	public String main() {
		return "main";
	}
	
	@ResponseBody // 요청했던 페이지로 응답: return 값을 출력한다.(ajax로 요청했다면 ajax메서드로 값을 전달)
	@GetMapping("/myinfo")
	public JSONObject myInfo(HttpServletRequest request) throws IOException, ParseException {
		System.out.println("나의정보조회[계좌목록]");

		HttpURLConnection conn=null;
		JSONObject result=null;
		
		//사용자 일련 번호를 가져오기 위해 session객체 구함
		HttpSession session=request.getSession();
		UserDto ldto=(UserDto)session.getAttribute("ldto");
		int userSeqNo=ldto.getUserseqno();//사용자 일련번호
		String useraccesstoken=ldto.getUseraccesstoken();//접근할 토큰
		
		//openbanking API 요청코드
		URL url=new URL("https://testapi.openbanking.or.kr/v2.0/user/me?"
				      + "user_seq_no="+userSeqNo);
		
		conn=(HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization","Bearer "+useraccesstoken);
		conn.setDoOutput(true);
		
		// java에서 사용할 수 있도록 읽어들이는 코드
		BufferedReader br=new BufferedReader(
					new InputStreamReader(conn.getInputStream(),"utf-8")
				);
		
		StringBuilder response=new StringBuilder();
		String responseLine=null;
		
		while((responseLine=br.readLine())!=null) {
			response.append(responseLine.trim());
		}
		
		// json형태의 문자열을 json객체로 변환 -> 값을 가져오기 편함
		result=(JSONObject)new JSONParser().parse(response.toString());
		System.out.println("result:"+result.get("res_list"));
		
		
		return result;
	}
	
	//Feign 적용할 경우
	//나의정보조회
	@GetMapping("/myinfo_feign")
	public String myinfo_feign(HttpServletRequest request,
			                   Model model) throws IOException, ParseException {
		System.out.println("나의정보조회[계좌목록]");

		//사용자 일련 번호를 가져오기 위해 session객체 구함
		HttpSession session=request.getSession();
		UserDto ldto=(UserDto)session.getAttribute("ldto");
		int userSeqNo=ldto.getUserseqno();//사용자 일련번호
		String useraccesstoken=ldto.getUseraccesstoken();//접근할 토큰
		
		//json값들을 userMeDto에 저장
		UserMeDto userMeDto=openBankingFeign
				.requestUserMe("Bearer "+useraccesstoken, userSeqNo+"");
		//자바객체에 결과값을 저장했으므로 Model에 담아서 JSP로 전달할 수 있다.
		model.addAttribute("userMeDto", userMeDto);
		System.out.println("계좌목록수:"+userMeDto.getRes_list().size());
		return "main";
	}
	
	@ResponseBody
	@GetMapping("/balance")
	public JSONObject balance(String fintech_use_num,HttpServletRequest request) throws IOException, ParseException {
		System.out.println("잔액조회하기");
		HttpURLConnection conn=null;
		JSONObject result=null;
		
		HttpSession session=request.getSession();
		UserDto ldto=(UserDto)session.getAttribute("ldto");
		
		URL url=new URL("https://testapi.openbanking.or.kr/v2.0/account/balance/fin_num?"
				      + "bank_tran_id=M202201886U"+createNum()
				      + "&fintech_use_num="+fintech_use_num
				      + "&tran_dtime="+getDateTime());
		
		conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", "Bearer "+ldto.getUseraccesstoken());
		conn.setDoOutput(true);
		
		// java에서 사용할 수 있도록 읽어들이는 코드
		BufferedReader br=new BufferedReader(
					new InputStreamReader(conn.getInputStream(),"utf-8")
				);
		StringBuilder response=new StringBuilder();
		String responseLine=null;
		
		while((responseLine=br.readLine())!=null) {
			response.append(responseLine.trim());
		}
		
		result=(JSONObject)new JSONParser().parse(response.toString());
		System.out.println("잔액:"+result.get("balance_amt"));
		
		return result;
	}
	
	//거래내역 조회
	@GetMapping("/transactionList")
	@ResponseBody
	public JSONObject transactionList(String fintech_use_num
			                         ,HttpServletRequest request) throws IOException, ParseException {
		System.out.println("거래내역 조회하기");
		HttpURLConnection conn=null;
		JSONObject result=null;
		
		HttpSession session=request.getSession();
		UserDto ldto=(UserDto)session.getAttribute("ldto");
		
		URL url=new URL("https://testapi.openbanking.or.kr/v2.0/account/transaction_list/fin_num?"
				      + "bank_tran_id=M202201886U"+createNum()
				      + "&fintech_use_num="+fintech_use_num
				      + "&inquiry_type=A"
				      + "&inquiry_base=D"
				      + "&from_date=20190101"
				      + "&to_date=20190131"
				      + "&sort_order=D"
				      + "&tran_dtime="+getDateTime());
		
		conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", "Bearer "+ldto.getUseraccesstoken());
		conn.setDoOutput(true);
		
		// java에서 사용할 수 있도록 읽어들이는 코드
		BufferedReader br=new BufferedReader(
					new InputStreamReader(conn.getInputStream(),"utf-8")
				);
		StringBuilder response=new StringBuilder();
		String responseLine=null;
		
		while((responseLine=br.readLine())!=null) {
			response.append(responseLine.trim());
		}
		
		//읽은 값이 json 형태로 된 문자열 --> json객체로 변환하자
		result=(JSONObject)new JSONParser().parse(response.toString());
		System.out.println("거래내역:"+result.get("res_list"));
		
		return result;
	}
	
	//계좌등록
	@ResponseBody
	@GetMapping("/addaccount")
	public String addAccount(String code) {
		System.out.println("계좌등록하기");
		System.out.println("code:"+code);
		//팝업창을 닫아 주기 위해서
		String str="<script type='text/javascript'>"
				  +"     self.close();"
				  +"</script>";
		return str;
	}
	
	//이용기관 부여번호 9자리를 생성하는 메서드
	public String createNum() {
		String createNum="";
		for (int i = 0; i < 9; i++) {
			createNum+=((int)(Math.random()*10))+"";
		}
		System.out.println("이용기관부여번호9자리생성:"+createNum);
		return createNum;
	} 
	//현재시간 구하는 메서드
	public String getDateTime() {
		LocalDateTime now=LocalDateTime.now(); //현재시간 구하기
		String formatNow=now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		return formatNow;
	}
}








