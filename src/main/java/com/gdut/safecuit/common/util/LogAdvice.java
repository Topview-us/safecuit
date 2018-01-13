package com.gdut.safecuit.common.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by Garson in 11:31 2018/1/12
 * Description : 调用方法的记录
 */
@Order(0)
@Aspect
@Component
public class LogAdvice {

	@Pointcut("execution(* com.gdut.safecuit..*.*(..))")
	public void pointCut(){}

	@Before("pointCut()")
	public void before(JoinPoint joinPoint){
		Signature signature = joinPoint.getSignature();
		LogUtil.info(getClass(),"---execute method: " + signature.getDeclaringTypeName() + "." + signature.getName());
	}

	@AfterThrowing(value = "pointCut()" ,throwing = "e")
	public void afterThrow(JoinPoint joinPoint ,Exception e){
		Signature signature = joinPoint.getSignature();
		LogUtil.error(getClass(),"---execute method: " + signature.getDeclaringTypeName() + "." + signature.getName()
				+ "---Throw Exception: " + e,e);
	}

}
