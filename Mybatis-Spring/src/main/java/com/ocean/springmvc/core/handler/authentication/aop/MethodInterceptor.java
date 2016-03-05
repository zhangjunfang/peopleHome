package com.ocean.springmvc.core.handler.authentication.aop;

import java.io.Serializable;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ocean.springmvc.core.constant.Constant;
import com.ocean.springmvc.core.context.Context;

/**
 * 
 * @author ocean 声明一个切面类
 */
@Aspect
@Service
public class MethodInterceptor implements Serializable {

	@Autowired(required = false)
	private MethodFilter methodFilter;
	private static final long serialVersionUID = 960785547282732548L;

	/**
	 * 声明一个切入点
	 */
	@Pointcut("execution(* com.*.*.service..*Service*.*(..))")
	public void anyMethod() {
	}

	/**
	 * 声明一个前置通知
	 */
	@Before("anyMethod()")
	public void beforeAdvide(JoinPoint joinPoint) {
		
		if (null != methodFilter) {
			methodFilter.isAuthentication(Context.getContext().getAttribute(Constant.SUBJECT),joinPoint.getSignature().toString(),
					joinPoint.getArgs());
		}

		System.out.println("前置通知");
	}

	/**
	 * 声明一个后置通知
	 */
	@After("anyMethod()")
	public void afterAdvie(JoinPoint jp) {
		System.out.println("后置通知:");
	}

	/**
	 * 声明一个异常通知
	 */
	@AfterThrowing(pointcut = "anyMethod()", throwing = "throwing")
	public void afterThrowsAdvice(JoinPoint point, RuntimeException throwing) {
		System.out.println("异常通知:" + throwing.getMessage());
	}

}
