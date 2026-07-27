package com.example.demo;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(TaskNotFoundException.class)
	public ModelAndView handleTaskNotFound(TaskNotFoundException e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", e.getMessage());
		mv.setViewName("404");
		return mv;
	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleUnexpected(Exception e) {
		e.printStackTrace();
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", "予期しないエラーが発生しました");
		mv.setViewName("500");
		return mv;
	}
}
