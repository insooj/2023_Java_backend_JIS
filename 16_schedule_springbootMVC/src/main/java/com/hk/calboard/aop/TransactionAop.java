package com.hk.calboard.aop;

import java.util.ArrayList;
import java.util.List;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@Configuration
public class TransactionAop {

	@Autowired
	private PlatformTransactionManager transactionManager;
	
	private final String EXECUTION 
	    = "execution(* com.hk.calboard.service.CalServiceImp.*(..))";
	
	@Bean
	public TransactionInterceptor transactionAdvice() {
		//rollback 규칙 작성
		List<RollbackRuleAttribute> rollbackRules =
				new ArrayList<RollbackRuleAttribute>();
		rollbackRules.add(new RollbackRuleAttribute(Exception.class));
		
		//모든 규칙 적용하기
		RuleBasedTransactionAttribute transactionAttribute
		=new RuleBasedTransactionAttribute();
		transactionAttribute.setRollbackRules(rollbackRules);
		transactionAttribute.setName("*");
		
		MatchAlwaysTransactionAttributeSource attributeSource
		=new MatchAlwaysTransactionAttributeSource();
		attributeSource.setTransactionAttribute(transactionAttribute);
		
		TransactionInterceptor interceptor=new TransactionInterceptor();
		interceptor.setTransactionManager(transactionManager);
		interceptor.setTransactionAttributeSource(attributeSource);
		
		return interceptor;
	}
	
	@Bean
	public Advisor transactionAdvisor() {
		AspectJExpressionPointcut pointcut 
		= new AspectJExpressionPointcut();
		
		pointcut.setExpression(EXECUTION);
		
		return new DefaultPointcutAdvisor(pointcut,transactionAdvice());
	}
}












