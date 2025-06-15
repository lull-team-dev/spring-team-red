package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.repository.UserRepository;

@Controller
public class RegisterController {

	@Autowired
	UserRepository userRepository;

	// 入力画面の表示
	@GetMapping("/register")
	public String register() {
		return "register";
	}

}
