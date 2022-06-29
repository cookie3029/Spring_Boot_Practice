package com.paori.mobileServer.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAOP {

	// 모든 POST 요청에 대해서 반응하는 메서드
	// 아래에 있는 afterReturning 함수의 모든 내용을 Post 요청을 수행하는 메서드의 가장 하단에 추가된
	// 형태의 새로운 클래스를 내부적으로 만듭니다.
	// 우리가 Controller 클래스의 메서드를 호출하면 내부에 만든 클래스의 메서드를 호출
	@Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
	public void postMapping() {}
	
	@AfterReturning(pointcut = "postMapping()", returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		System.out.println("모든 POST 요청에 반응");
	}
	
	// Controller의 메서드에 대해서 반응
	@Pointcut("execution(* com.paori.mobileServer.controller.*.*(..))")
	public void thing() {}
	
	@Around("thing()")
	public Object doStuffBeforeThing(ProceedingJoinPoint joinPoint) {
		Class<?> clazz = joinPoint.getTarget().getClass();
		Logger logger = LoggerFactory.getLogger(clazz);
		Object result = null;
		
		try {
			logger.info("Controller의 작업을 처리하기 전에 수행할 내용");
			
			// 호출한 메서드를 실행
			result = joinPoint.proceed(joinPoint.getArgs());
			
			logger.info("Controller의 작업을 처리한 후 수행할 내용");
		} catch (Throwable e) {
			System.out.println(e.getLocalizedMessage());
		}
		return result;
	}
}
