package com.example.demo.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.example.demo.entity.User;
import com.example.demo.exception.EmailAlreadyExistsException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Controller
public class RegisterController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	// 入力画面の表示
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@GetMapping("/checkEmail")
	@ResponseBody
	public boolean checkEmail(@RequestParam String email) {
		return !userService.existsByEmail(email);
	}

	// 確認画面から入力画面へ戻った時の表示
	@PostMapping(value = "/register", params = "redo")
	public String returnToRegister(@ModelAttribute("user") User user, BindingResult br, Model model) {
		// 入力された User オブジェクトがバインドされ、再表示に使えます
		return "register";
	}

	// 確認画面の表示
	@PostMapping("/registerConfirm")
	public String confirm(@ModelAttribute("user") @Valid User user, BindingResult br, Model model) {
		if (br.hasErrors()) {
			return "register"; // エラー時、再入力へ
		}
		model.addAttribute("user", user);
		return "registerConfirm";
	}

	// 完了画面の表示
	@PostMapping("/registerComplete")
	public String complete(@ModelAttribute("user") @Valid User user, BindingResult br, RedirectAttributes ra) {
		if (br.hasErrors()) {
			return "registerConfirm";
		}

		// A: メール重複チェック
		if (userService.existsByEmail(user.getEmail())) {
			br.rejectValue("email", "duplicate", "このメールは既に使われています");
			return "registerConfirm";
		}

		try {
			userService.save(user);// B: 例外発生時キャッチ
		} catch (EmailAlreadyExistsException ex) {
			// B: DB制約での重複例外キャッチ
			br.rejectValue("email", "duplicate", ex.getMessage());
			return "registerConfirm";
		}
		// フラッシュ属性としてユーザー情報を設定
		ra.addFlashAttribute("user", user);
		return "registerComplete";
	}

	@GetMapping("/registerComplete")
	public String showComplete(Model model, HttpServletRequest request) {
		// フラッシュ属性を取得
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null && flashMap.containsKey("user")) {
			model.addAttribute("user", flashMap.get("user"));
		}
		return "registerComplete";
	}

}
