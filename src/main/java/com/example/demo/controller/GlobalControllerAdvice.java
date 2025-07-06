package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.model.Account;

@ControllerAdvice
public class GlobalControllerAdvice {
	@ModelAttribute("loginUser")
	public void loginUser(HttpSession session, Model model) {
		Account user = (Account) session.getAttribute("user");
		model.addAttribute("user", user);
	}
}
