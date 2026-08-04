package com.example.demo;

import java.util.List;

public class TaskPage {
	private final List<Task> tasks;
	private final int page;
	private final int size;
	private final int totalPages;
	private final boolean hasPrev;
	private final boolean hasNext;
	
	public TaskPage(List<Task> tasks, int page, int size, int totalPages, boolean hasPrev, boolean hasNext) {
		this.tasks = tasks;
		this.page = page;
		this.size = size;
		this.totalPages = totalPages;
		this.hasPrev = hasPrev;
		this.hasNext = hasNext;
	}
	
	public List<Task> getTasks() {
		return tasks;
	}
	
	public int getPage() {
		return page;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getTotalPages() {
		return totalPages;
	}
	
	public boolean isHasPrev() {
		return hasPrev;
	}
	
	public boolean isHasNext() {
		return hasNext;
	}
}
