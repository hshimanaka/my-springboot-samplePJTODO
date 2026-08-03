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
	//userIdでの検索
	public List<Task> findByUsername(String username) {
		return taskMapper.findByUsername(username);
	}
	public List<Task> findByUsernamePaged(String username, int page, int size) {
		return taskMapper.findByUsernamePaged(username, page, size);
	}
	//保存
	public void save(Task task) {
		if(task.getId() == null) {
			taskMapper.insert(task);
		} else {
			taskMapper.update(task);
		}
	}
	//userIdとIdで検索
	public Optional<Task> findByIdAndUsername(Long id, String username) {
		return taskMapper.findByIdAndUsername(id, username);
	}
	//削除
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