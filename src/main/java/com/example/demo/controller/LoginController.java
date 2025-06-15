package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.model.Account;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	@Autowired
	HttpSession session;
	
	@Autowired
	Account account;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping({"/","login"})
	public String index(
	@RequestParam(name = "error", defaultValue = "") String error,
	Model model) {
				// エラーパラメータのチェック
	if (error.equals("notLoggedIn")) {
	model.addAttribute("message", "ログインしてください");
	}
	return "login";
	}
	
	
	@PostMapping("/login")
	public String login(
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			Model model
			) {
		
		List<String> errorList = new ArrayList<>();
		
		// 名前が空の場合にエラー
		if (email.isEmpty() || password.isEmpty()) {
			errorList.add("メールアドレスとパスワードは必須です");
		}		
		
		if (!email.contains("@")) { //指定した値が入っているか
		    errorList.add("正しいメールアドレスを入力してください（@が必要です）");
		}
		
		if (!errorList.isEmpty()) {
		        model.addAttribute("errorList", errorList);
		        return "login";
		    }
		
		List<User> userList = userRepository.findByEmailAndPassword(email, password);
		
		if (userList == null || userList.size() == 0) {
			// 存在しなかった場合
			model.addAttribute("message", "メールアドレスとパスワードが一致しませんでした");
			
			return "login";
		}
		
		User user = userList.get(0);
		
		
		// セッション管理されたアカウント情報にIDと名前をセット
		account.setId(user.getId());
		account.setName(user.getName());
		
		 //session.setAttribute("userId", user.getId());
		 //session.setAttribute("userName", user.getName());

		// 「/items」へのリダイレクト
		return "redirect:/hotel";
		
		}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		// セッション情報を全てクリアする
		session.invalidate();
		return "redirect:/login";
	
	}

}
