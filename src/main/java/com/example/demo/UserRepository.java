package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
	private final UserMapper loginMapper;
	public UserRepository(UserMapper loginMapper) {
		this.loginMapper = loginMapper;
	}
	
	//usernameでの検索
	public Optional<User> findByUsername(String username) {
		return loginMapper.findByUsername(username);
	}
	//保存
	public void save(User user) {
		if(user.getId() == null) {
			loginMapper.insert(user);
		} else {
			loginMapper.update(user);
		}
	}
	//削除
	public void delete(String username) {
		loginMapper.delete(username);
	}
}