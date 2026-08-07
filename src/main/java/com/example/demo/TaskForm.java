package com.example.demo;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TaskForm {
	private Long id;
	@NotBlank(message = "タイトルは必須です")
	@Size(max = 255, message = "タイトルは255文字以内で入力してください")
	private String title;
	@Size(max = 1000, message = "内容は1000文字いないで入力してください")
	private String content;
	private LocalDate startDate;
	private LocalDate endDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
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
	//新規作成用
	public Task toEntity(String username) {
		Task task = new Task();
		task.setUsername(username);
		task.setTitle(this.title);
		task.setContent(this.content);
		task.setStartDate(this.startDate);
		task.setEndDate(endDate);
		return task;
	}
	
	//更新用
	public Task toEntityForUpdate(String username) {
		Task task = toEntity(username);
		task.setId(this.id);
		return task;
	}
	
	//編集画面の初期表示
	public static TaskForm form(Task task) {
		TaskForm form = new TaskForm();
		form.setId(task.getId());
		form.setTitle(task.getTitle());
		form.setContent(task.getContent());
		form.setStartDate(task.getStartDate());
		form.setEndDate(task.getEndDate());
		return form;
	}
}
