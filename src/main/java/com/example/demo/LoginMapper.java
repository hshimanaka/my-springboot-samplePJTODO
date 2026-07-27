package com.example.demo;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginMapper {
	List<User> findByPasswordAndUsername(@Param("password") String password, @Param("username") String username);
	Optional<User> findByUsername(@Param("username") String username);
	void insert(User login);
	void update(User login);
	void delete(String username);
} 
