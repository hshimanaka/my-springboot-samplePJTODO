package com.example.demo;
import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomePageController {
	private final TaskService taskService;
	private final LoginService loginService;
	private final HttpSession session;
	
	public HomePageController(
			TaskService taskService,
			LoginService loginService,
			HttpSession session) {
		this.taskService = taskService;
		this.loginService = loginService;
		this.session = session;
	}
	
	private String getLoginUsername() {
		String username = (String) session.getAttribute("username");
		if(username == null) {
			throw new IllegalStateException("ログインしていません");
		}
		return username;
	}
	
	@GetMapping("/")
	public String homePage() {
		return "homePage";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@PostMapping("/login") 
	public String loginCheck(
	    @RequestParam("password") String password,
	    @RequestParam("username") String username
	    ) {
		try {
			User user = loginService.loginCheck(password, username);
			session.setAttribute("username", user.getUsername());
			return "redirect:/tasks";
		} catch(LoginNotFoundException e) {
			return "login";
		}
	}
	@GetMapping("/tasks/new")
	public String newTask() {
		return "newTasks";
	}
	
	@GetMapping("/tasks")
	public ModelAndView showTasks(ModelAndView mv) {
		String username = getLoginUsername();
		List<Task> taskList = taskService.findByUsername(username);
		mv.addObject("tasks", taskList);
		mv.setViewName("tasks");
		return mv;
	}
	@PostMapping("/tasks")
	public String createTask(Task task) {
		String username = (String) session.getAttribute("username");
		task.setUsername(username);
		taskService.create(task);
		return "redirect:/tasks";
	}

	
	@PostMapping("/tasks/delete")
	public String delete(@RequestParam("id") Long id) {
		String username = getLoginUsername();
		taskService.delete(id, username);
		return "redirect:/tasks";
	}
	
	@GetMapping("/tasks/edit")
	public ModelAndView taskEdit(@RequestParam("id") Long id, ModelAndView mv) {
	    String username = getLoginUsername();
	    Task targetTask = taskService.findByIdAndUsername(id, username);
	    mv.addObject("task", targetTask);
	    mv.setViewName("taskEdit");
	    return mv;
	}
	@PostMapping("/tasks/update")
	public ModelAndView updateTask(
	        @RequestParam("id") Long id,
	        @RequestParam("title") String title,
	        @RequestParam("content") String content,
	        @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	        @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
	        ModelAndView mv
	        ){
	    String username = getLoginUsername();
	    Task task = new Task();
	    task.setId(id);
	    task.setTitle(title);
	    task.setContent(content);
	    task.setUsername(username);
	    task.setStartDate(startDate);
	    task.setEndDate(endDate);
	    taskService.update(task);
	    List<Task> taskList = taskService.findByUsername(username); 
	    mv.addObject("tasks", taskList);
	    mv.setViewName("tasks");
	    return mv;
	}

	
	@PostMapping("/register")
	public String registerUser(
			@RequestParam("username") String formusername,
			@RequestParam("password") String formpassword) {
		loginService.register(formusername, formpassword);
		return "redirect:/login";
	}
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/login";
	}
}