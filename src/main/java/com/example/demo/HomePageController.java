package com.example.demo;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomePageController {
	@Autowired
	private LoginRepository loginRepository;
	@Autowired
	private TaskRepository taskRepository;
	
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
	    @RequestParam("username") String formUsername,
	    @RequestParam("password") String formPassword
	    ) {
	  loginprocess dbUser = loginRepository.findByUsername(formUsername); 
		if (dbUser != null && dbUser.getPassword().equals(formPassword)) {
			return "tasks";
		} else {
			return "login";
		}
	}
	@GetMapping("/tasks/new")
	public String newTask() {
		return "newTasks";
	}
	
	@PostMapping("/tasks")
	public ModelAndView createTask(
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam("username") String username,
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start_date,
			@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end_date,
			ModelAndView model
			) {
		Task task = new Task();
		task.setTitle(title);
		task.setContent(content);
		task.setUsername(username);
		task.setStart_date(start_date);
		task.setEnd_date(end_date);
		taskRepository.save(task);
		List <Task> taskList = taskRepository.findAll();
		model.addObject("tasks", taskList);
		model.setViewName("tasks");
		return model;
	}
	@GetMapping("/tasks/delete")
	public ModelAndView deletId(@RequestParam("id") Long id, ModelAndView mv) {
		taskRepository.deleteById(id);
		List<Task> taskList = taskRepository.findAll();
		mv.addObject("tasks", taskList);
		mv.setViewName("tasks");
		return mv;
	}
	
	@GetMapping("/tasks/edit")
	public ModelAndView taskEdit(@RequestParam("id") Long id, ModelAndView mv) {
		Task targetTask = taskRepository.findById(id).get();
		List<Task> taskList = taskRepository.findAll();
		mv.addObject("task", targetTask);
		mv.setViewName("taskEdit");
		return mv;
	}
	@PostMapping("/tasks/update")
	public ModelAndView upDateTask(
			@RequestParam("id") Long id,
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam("username") String username,
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start_date,
			@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end_date,
			ModelAndView mv
			){
		Task task = new Task();
		task.setId(id);
		task.setTitle(title);
		task.setContent(content);
		task.setUsername(username);
		task.setStart_date(start_date);
		task.setEnd_date(end_date);
		taskRepository.save(task);
		List <Task> taskList = taskRepository.findAll();
		mv.addObject("tasks", taskList);
		mv.setViewName("tasks");
		return mv;
	}
	@GetMapping("/tasks")
	public ModelAndView showtasks(ModelAndView mv) {
		List<Task> taskList = taskRepository.findAll();
		mv.addObject("tasks", taskList);
		mv.setViewName("tasks");
		return mv;
	}
	@PostMapping("/register")
	public String registerUser(
			@RequestParam("username") String formusername,
			@RequestParam("password") String formpassword) {
		loginprocess newUser = new loginprocess();
		newUser.setUsername(formusername);
		newUser.setPassword(formpassword);
		loginRepository.save(newUser);
		return "redirect:/login";
	}
	@GetMapping("/logout")
	public String lorout() {
		return "login";
	}
}