package com.example.demo;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TaskForm {
	@NotBlank(message = "タイトルは必須です")
	@Size(max = 255, message = "タイトルは255文字以内で入力してください")
	private String title;
	
	@Size(max = 1000, message = "内容は1000文字いないで入力してください")
	private String content;
	
	@NotNull(message = "開始日は必須です")
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	
	
	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public LocalDate getStartDate() {
		return startDate;
	}



	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}



	public LocalDate getEndDate() {
		return endDate;
	}



	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}



	public Task toEntity(String username) {
		Task task = new Task();
		task.setUsername(username);
		task.setTitle(this.title);
		task.setContent(this.content);
		task.setStartDate(this.startDate);
		task.setEndDate(endDate);
		return task;
	}
}
