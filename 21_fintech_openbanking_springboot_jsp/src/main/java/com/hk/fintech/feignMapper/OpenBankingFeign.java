package com.hk.fintech.feignMapper;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.hk.fintech.apidto.UserMeDto;

//restAPI 서버(openbanking)에 요청하고, 결과값을 받아 주는 기능 
@FeignClient(name = "feign", url = "https://testapi.openbanking.or.kr")
public interface OpenBankingFeign {

	//나의 정보조회
	@GetMapping(path = "/v2.0/user/me")
	public UserMeDto requestUserMe(
			@RequestHeader("Authorization") String access_token,
			@RequestParam("user_seq_no") String user_seq_no
			);
	
	//토큰발급
//	@PostMapping(params = "/oauth/v2.0/token",
//			     consumes = "application/x-www-form-urlencoded",
//			     produces = "application/json")
//	public TokenResponseDto requestToken(
//				@RequestParam("code") String code,
//				@RequestParam("client_id") String client_id,
//				@RequestParam("client_secret") String client_secret,
//				@RequestParam("redirect_uri") String redirect_uri,
//				@RequestParam("grant_type") String grant_type
//			);
}








