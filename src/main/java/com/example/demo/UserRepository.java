package com.example.demo;

import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
	private final UserMapper userMapper;
	public UserRepository(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	public Optional<User> findByUsername(String username) {
		return userMapper.findByUsername(username);
	}
	public void save(User user) {
		if(user.getId() == null) {
			userMapper.insert(user);
		} else {
			userMapper.update(user);
		}
	}
	public void delete(String username) {
		userMapper.delete(username);
	}
}