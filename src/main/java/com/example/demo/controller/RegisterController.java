package com.example.demo.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.UserRegisterForm;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Controller
public class RegisterController {

	@Autowired
	UserRepository userRepository;

	// 入力画面の表示
	@GetMapping("/register")
	public String register(Model model) {

		// バリデーション用のインスタンスを作成
		model.addAttribute("registerForm", new UserRegisterForm());
		return "register";
	}

	// 確認画面から入力画面へ戻った時の表示
	@PostMapping(value = "/register", params = "redo")
	public String returnToRegister(@ModelAttribute UserRegisterForm registerForm,
			BindingResult result,
			Model model) {

		// 入力された User オブジェクトがバインドされ、再表示に使えます
		model.addAttribute("registerForm", registerForm);
		return "register";
	}

	// 確認画面の表示
	@PostMapping("/registerConfirm")
	public String confirm(@ModelAttribute("registerForm") @Valid UserRegisterForm registerForm,
			BindingResult result,
			@RequestParam("passConfirm") String passConfirm,
			Model model) {

		if (registerForm != null) {
			// Email重複チェック
			if (userRepository.findByEmail(registerForm.getEmail()).isPresent()) {
				result.rejectValue("email", "duplicate", "このメールアドレスはすでに登録されています");
			}

			// パスワードとパスワード確認の一致チェック
			if (!registerForm.getPassword().equals(passConfirm)) {
				result.rejectValue("password", "duplicate", "パスワードが一致しません");
			}
		}

		// バリデーションエラーがあればHTMLに
		if (result.hasErrors()) {
			model.addAttribute("registerForm", registerForm);
			return "register";
		}

		// 問題なければこちら
		model.addAttribute("registerForm", registerForm);
		return "registerConfirm";
	}

	//完了画面の表示とユーザーのDB保存処理
	@PostMapping("/registerComplete")
	public String complete(
			@ModelAttribute("registerForm") UserRegisterForm registerForm,
			Model model) {
		// 上記バリデーションを通ってきているから、ここでバリデーションは必要ないのでは？

		// DTOからエンティティへ変換するよー
		User user = new User();
		user.setLastName(registerForm.getLastName());
		user.setFirstName(registerForm.getFirstName());
		user.setLastNameKana(registerForm.getLastNameKana());
		user.setFirstNameKana(registerForm.getFirstNameKana());
		user.setAddress(registerForm.getAddress());
		user.setTel(registerForm.getTel());
		user.setEmail(registerForm.getEmail());
		user.setPassword(registerForm.getPassword());

		// Userテーブルに保存
		userRepository.save(user);

		model.addAttribute("user", user);
		return "registerComplete";
	}

}
