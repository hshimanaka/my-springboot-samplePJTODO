package com.example.demo;

public class LoginNotFoundException  extends RuntimeException {
    public LoginNotFoundException(String password) {
        super("パスワードが違います");
    }
}