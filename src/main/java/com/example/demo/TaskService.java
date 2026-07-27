package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {
	private final TaskRepository taskRepository;
	
	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}
	
	@Transactional(readOnly = true)
		public List<Task> findByUsername(String username) {
			return taskRepository.findByUsername(username);
		}
	
	@Transactional(readOnly = true)
	public Task findByIdAndUsername(Long id, String username) {
		return taskRepository.findByIdAndUsername(id,  username)
				.orElseThrow(() -> new TaskNotFoundException(id));
	}
	
	@Transactional
	public void create(Task task) {
		taskRepository.save(task);
	}
	
	@Transactional
	public void update(Task task) {
		taskRepository.save(task);
	}
	
	@Transactional
	public void delete(Long id, String username) {
		Task task = taskRepository.findByIdAndUsername(id, username)
				.orElseThrow(() -> new TaskNotFoundException(id));
		taskRepository.delete(id, username);
	}
	
}
