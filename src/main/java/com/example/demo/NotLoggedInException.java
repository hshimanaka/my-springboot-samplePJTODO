package com.example.demo;

public class NotLoggedInException extends RuntimeException {
	public NotLoggedInException(String message) {
		super(message);
	}
}
