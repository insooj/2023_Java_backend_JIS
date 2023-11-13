package com.hk.calboard.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component // 내가 만든 클래스를 등록
@Aspect
public class LogAspect {
	
//	@Bean // 외부 라이브러리 등록
//	public void test() {}
	
	Logger logger=LoggerFactory.getLogger(getClass());
	
	
	//execution(): 메서드단위로 설정 - com.hk.calboard.controller.CalController.*Board(..)
	//within():객체 단위 설정 - com.hk.calboard.controller.*
	@Pointcut(value="within(com.hk.calboard.service.*)")
	public void controller() {}
	
//	@Pointcut()
//	public void testcontroller() {}
	
	//before
	@Before(value = "controller()")
	public void before(JoinPoint join) {
		logger.info("before 메서드 실행:{}",join.getSignature().getName());
	}
	//AfterReturning
	//returning속성: 메서드에 정의한 파라미터에 반환값을 보내줌
	@AfterReturning(pointcut = "controller()", returning = "returnVal")
	public void afterReturning(JoinPoint join,Object returnVal) {
		logger.info("afterReturning메서드 실행:{}",join.getSignature().getName());
		logger.info("afterReturning메서드 실행:{}",join.getTarget()+"");
		if(returnVal==null) {
			return ;
		}else {
			logger.info("리턴값:{}",returnVal);
		}
	}
	//AfterThrowing
	@AfterThrowing(pointcut = "controller()", throwing = "e")
	public void afterThrowing(JoinPoint join, Exception e) {
		logger.info("afterThrowing메서드 실행:{}",join.toShortString());
		logger.info("오류내용:{}",e.getMessage());
	}
	
	
	//Around: 적용범위가 넒음 , before, afterReturning 모두 적용
}








