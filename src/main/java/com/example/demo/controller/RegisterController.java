package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.repository.UserRepository;

@Controller
@RequestMapping("/users")
public class RegisterController {

	@Autowired
	private UserRepository userRepository;

	// 入力画面の表示
	@GetMapping("/register")
	public String showRegisterForm() {
		return "register";
	}

	// 完了画面の表示
	@GetMapping("/complete")
	public String showCompletePage() {
		return "registerComplete";
	}

}
