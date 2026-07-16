package com.example.demo;

import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class login {
@Autowired
HttpSession session;

@Before("execution(* *..HomePageController.*(..))")
public void checkLogin(JoinPoint jp) {
	String username = (String) session.getAttribute("username");
	if(username == null) {
		System.out.println("保存できません");
	}
}
}
