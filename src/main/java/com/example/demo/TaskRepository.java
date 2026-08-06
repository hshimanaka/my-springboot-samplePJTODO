package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class TaskRepository{
	private final TaskMapper taskMapper;
	public TaskRepository(TaskMapper taskMapper) {
		this.taskMapper = taskMapper;
	}

	public List<Task> findByUsernamePaged(String username, int limit, int offset) {
		return taskMapper.findByUsernamePaged(username, limit, offset);
	}
	
	public void save(Task task) {
		if(task.getId() == null) {
			taskMapper.insert(task);
		} else {
			taskMapper.update(task);
		}
	}
	
	public Optional<Task> findByIdAndUsername(Long id, String username) {
		return taskMapper.findByIdAndUsername(id, username);
	}
	
	public void delete(Long id, String username) {
		taskMapper.delete(id, username);
	}
	public void update(Task task) {
		taskMapper.update(task);
	}
	public int countByUsername(String username) {
        return taskMapper.countByUsername(username);
    }
	
	
	

}