package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(TaskNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ModelAndView handleTaskNotFound(TaskNotFoundException e) {
		log.warn("タスクが見つかりません: {}", e.getMessage());
		ModelAndView mv = new ModelAndView("404");
		mv.addObject("message", e.getMessage());
		return mv;
	}
	

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView handleUnexpected(Exception e) {
		log.error("予期せぬエラーが発生しました", e); 
			ModelAndView mv = new ModelAndView("500");
			mv.addObject("message", "予期せぬエラーが発生しました");
			return mv;
	}
	
	@ExceptionHandler(NotLoggedInException.class)
	public String handleNotLoggedIn(NotLoggedInException e) {
		log.info("未ログインアクセス： {}", e.getMessage());
		return "redirect:/login";
	}
	
}
