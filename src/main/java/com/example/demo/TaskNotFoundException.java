package com.example.demo;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("指定されたタスクが見つかりませんでした。 id: " + id);
    }
}