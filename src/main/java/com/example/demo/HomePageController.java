package com.example.demo;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomePageController {
	private final SecurityFilterChain filterChain;
	private final TaskService taskService;
	private final UserService loginService;
	private final HttpSession session;
	
	public HomePageController(
			TaskService taskService,
			UserService loginService,
			HttpSession session, SecurityFilterChain filterChain) {
		this.taskService = taskService;
		this.loginService = loginService;
		this.session = session;
		this.filterChain = filterChain;
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
	
	@PostMapping("/login") 
	public String loginCheck(
	    @RequestParam("password") String password, @RequestParam("username") String username, Model model) {
		try {
			User user = loginService.loginCheck(password, username);
			session.setAttribute("username", user.getUsername());
			return "redirect:/tasks";
		} catch(AuthenticationFailedException e) {
			model.addAttribute("error", e.getMessage());
			return "login";
		}
	}
	@GetMapping("/tasks/new")
	public String newTask(Model model) {
		model.addAttribute("taskForm", new TaskForm());
		return "newTasks";
	}
	
	@GetMapping("/tasks")
	public ModelAndView showTasks(@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "20") int size,
			ModelAndView mv) {
		String username = getLoginUsername();
		int total = taskService.countByUsername(username);
		int totalPage = (int) Math.ceil((double) total / size);
		mv.addObject("tasks", taskService.findByUsername(username, page, size));
		mv.addObject("page", page);
		mv.addObject("totalPages", totalPage);
		mv.setViewName("tasks");
		return mv;
	}
	@PostMapping("/tasks")
	public String createTask(@Validated @ModelAttribute TaskForm form, BindingResult result) {
		if(result.hasErrors()) {
			return "newTasks";
		}
		String username = getLoginUsername();
		taskService.create(form.toEntity(username));
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
	public String updateTask(
	        @RequestParam("id") Long id,
	        @RequestParam("title") String title,
	        @RequestParam("content") String content,
	        @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	        @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
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
	   return "redirect:/tasks";
	}

	
	@PostMapping("/register")
	public String registerUser(@Validated @ModelAttribute("registerForm") RegisterForm form, BindingResult result) {
		if(result.hasErrors()) {
			return "register";
		}
		try {
			loginService.register(form.getUsername(), form.getPassword());
		} catch (DuplicateUsernameException e) {
			result.rejectValue("username", "duplicate", e.getMessage());
			return "register";
		}
		return "redirect:/login";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("registerForm", new RegisterForm());
		return "register";
	}
	
	@PostMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/login";
	}
}