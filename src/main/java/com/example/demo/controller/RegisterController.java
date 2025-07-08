package com.example.demo.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Controller
public class RegisterController {

	@Autowired
	UserRepository userRepository;

	// 入力画面の表示
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	// 確認画面から入力画面へ戻った時の表示
	@PostMapping(value = "/register", params = "redo")
	public String returnToRegister(@ModelAttribute("user") User user, BindingResult result, Model model) {
		// 入力された User オブジェクトがバインドされ、再表示に使えます
		model.addAttribute("user", user);
		return "register";
	}

	// 確認画面の表示
	@PostMapping("/registerConfirm")
	public String confirm(@ModelAttribute("user") @Valid User user, BindingResult result, Model model) {
		if (user == null) {
			model.addAttribute("user", new User());
			return "register";
		}

		// Email重複チェック
		if (user.getEmail() != null && userRepository.findByEmail(user.getEmail()).isPresent()) {
			result.rejectValue("email", "error.user", "このメールアドレスはすでに登録されています");
		}

		if (result.hasErrors()) {
			model.addAttribute("user", user);  // これ重要
			return "register";
		}

		model.addAttribute("user", user);
		return "registerConfirm";
	}


	//完了画面の表示とユーザーのDB保存処理
	@PostMapping("/registerComplete")
	public String complete(@ModelAttribute("user") @Valid User user, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "registerConfirm";
		}

		userRepository.save(user);
		model.addAttribute("user", user); // モデルに入れてViewで使えるようにする
		return "registerComplete";
	}

}
