package com.stackroute.keepnote.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/* Annotate this class with @Aspect and @Component */

@Aspect
@Component
public class LoggingAspect {
	/*
	 * Write loggers for each of the methods of Category controller, any particular method
	 * will have all the four aspectJ annotation
	 * (@Before, @After, @AfterReturning, @AfterThrowing).
	 */
private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
	
	@Before("execution(* com.stackroute.keepnote.controller.*.*(..))")
	public void beforelog(JoinPoint joinPoint) {
		logger.info("Inside Before Advice ....");
		logger.info("Target object method : "+ joinPoint.getSignature().getName());
	}
	
	@After("within(com.stackroute.keepnote.controller..*)")
	public void afterLog(JoinPoint joinPoint) {
		logger.info("Inside After Advice ......");
		logger.info("Target object method : "+ joinPoint.getSignature().getName());
	}
	
	@AfterReturning("within(com.stackroute.keepnote.controller..*)")
	public void afterReturningLog(JoinPoint joinPoint) {
		logger.info("Inside After Throwing Advice ......");
		logger.info("Target object method : "+ joinPoint.getSignature().getName());
	}
	
	@AfterThrowing("within(com.stackroute.keepnote.controller..*)")
	public void aftertThrowingLog(JoinPoint joinPoint) {
		logger.info("Inside After Throwing Advice ......");
		logger.info("Target object method : "+ joinPoint.getSignature().getName());
	}
}
