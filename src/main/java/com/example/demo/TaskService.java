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
		task.setId(null);
		taskRepository.save(task);
	}
	
	@Transactional
	public void update(Task task) {
		taskRepository.findByIdAndUsername(task.getId(), task.getUsername())
		.orElseThrow(() -> new TaskNotFoundException(task.getId()));
		taskRepository.update(task);
	}
	
	@Transactional
	public void delete(Long id, String username) {
		taskRepository.findByIdAndUsername(id, username)
				.orElseThrow(() -> new TaskNotFoundException(id));
		taskRepository.delete(id, username);
	}
	
	@Transactional(readOnly = true)
	public List<Task> findByUsername(String username, int page, int size) {
		int limit = Math.min(Math.max(size,  1), 100);
		int offset = Math.max(page, 0) * limit;
		return taskRepository.findByUsernamePaged(username, limit, offset);
	}
	
	@Transactional(readOnly = true)
	public int countByUsername(String username) {
	    return taskRepository.countByUsername(username);
	}
	
	@Transactional(readOnly = true)
	public TaskPage findPage(String username, int requestedPage, int requestedSize) {
		int size = Math.min(Math.max(requestedSize, 1), 100);
		int total = taskRepository.countByUsername(username);
		int totalPages = (total == 0) ? 1 : (int) Math.ceil((double) total / size);
		
		int page = Math.min(Math.max(requestedPage, 0), totalPages - 1);
		int offset = page * size;
		boolean hasPrev = page > 0;
		boolean hasNext = (page + 1) < totalPages;
		
		List<Task> tasks = taskRepository.findByUsernamePaged(username, size, offset);
		return new TaskPage(tasks, page, size, totalPages, hasPrev, hasNext);
	}
}
