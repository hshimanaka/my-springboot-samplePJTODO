package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TaskMapper {
		Optional<Task> findByIdAndUsername(@Param("id") Long id, @Param("username") String username);
		void insert(Task task);
		void update(Task task);
		void delete(@Param("id") Long id, @Param("username") String username);
		
		List<Task> findByUsernamePaged(@Param("username") String username,
				@Param("limit") int limit,
				@Param("offset") int offset);
		int countByUsername(@Param("username") String username);
	
}