package com.example.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Aspect
@Component
public class SessionCheckAspect {

	
	 @Autowired
	    private HttpSession session;

	    @Around("@annotation(com.example.demo.annotation.LoginRequired)")
	    public Object checkLogin(ProceedingJoinPoint joinPoint) throws Throwable {
	        Object user = session.getAttribute("user");

	        if (user == null) {
	            // セッション切れ → ログインページへリダイレクト
	            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	            if (response != null) {
	                response.sendRedirect("/session-expired");
	            }
	            return null;
	        }

	        return joinPoint.proceed();
	    }
	}

