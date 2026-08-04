package com.example.demo;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
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
	private final TaskService taskService;
	private final UserService userService;
	private final HttpSession session;
	
	public HomePageController(
		TaskService taskService,
		UserService userService, HttpSession session) {
		this.taskService = taskService;
		this.userService = userService;
		this.session = session;
	}
	
	private String getLoginUsername() {
		String username = (String) session.getAttribute("username");
		if(username == null) {
			throw new NotLoggedInException("ログインしていません");
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
			User user = userService.loginCheck(password, username);
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
	public Model showTask(@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "20") int size,
			Model mv) {
		mv.addAttribute("taskPage", taskService.findPage(getLoginUsername(), page, size));
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

	
	@PostMapping("/tasks/{id}/delete")
	public String delete(@RequestParam("id") Long id) {
		String username = getLoginUsername();
		taskService.delete(id, username);
		return "redirect:/tasks";
	}
	
	@GetMapping("/tasks/edit")
	public String taskEdit(@RequestParam("id") Long id, Model mv) {
	    String username = getLoginUsername();
	    Task targetTask = taskService.findByIdAndUsername(id, username);
	    TaskForm form = new TaskForm();
	    form.setId(targetTask.getId());
	    form.setTitle(targetTask.getTitle());
	    form.setContent(targetTask.getContent());
	    form.setStartDate(targetTask.getStartDate());
	    form.setEndDate(targetTask.getEndDate());
	    mv.addAttribute("taskForm", form);
	   return "taskEdit";
	}
	
	@PostMapping("/tasks/update")
	public String updateTask(@Validated @ModelAttribute("taskForm") TaskForm form, BindingResult result) {
		if(result.hasErrors()) {
			return "taskEdit";
		}
		return "redirect:/tasks";
	}
	

	@PostMapping("/register")
	public String registerUser(@Validated @ModelAttribute("registerForm") RegisterForm form, BindingResult result) {
		if(result.hasErrors()) {
			return "register";
		}
		try {
			userService.register(form.getUsername(), form.getPassword());
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