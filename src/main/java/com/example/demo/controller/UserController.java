package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Reservation;
import com.example.demo.entity.User;
import com.example.demo.model.Account;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.UserRepository;




@Controller
public class UserController {
	@Autowired
	Account account;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ReservationRepository reservationRepository;
	
	//マイページ表示
	@GetMapping("/mypage/{id}")
	public String index(
			@PathVariable("id") Integer id,
			@RequestParam ("lastName") String lastName,
			@RequestParam ("firstName") String firstName,
			@RequestParam ("address") String address,
			@RequestParam ("tel") String tel,
			@RequestParam ("email") String email,
			Model model) {
		
		User user = userRepository.findById(id).get();
		Reservation reservation =reservationRepository.findById(id).get();
		
		model.addAttribute("user",user);
		model.addAttribute("reservation",reservation);
		
		return "mypage";
	}

	
	//ユーザー情報更新（表示）
	@GetMapping("/mypage/{id}/edit")
	public String edit(
			@PathVariable("id") Integer id,
			@RequestParam ("lastName") String lastName,
			@RequestParam ("firstName") String firstName,
			@RequestParam ("address") String address,
			@RequestParam ("tel") String tel,
			@RequestParam ("email") String email,
			@RequestParam ("password") String password,
			Model model) {
		
		User user = userRepository.findById(id).get();
		
		model.addAttribute("user",user);
		
		return "edit";
	}
	
	//ユーザー情報更新（編集）
	@PostMapping("/mypage/{id}/edit")
	public String update(
			@PathVariable("id") Integer id,
			@RequestParam (name = "lastName",defaultValue = "") String lastName,
			@RequestParam (name = "firstName",defaultValue = "") String firstName,
			@RequestParam (name = "lastNameKana",defaultValue = "") String lastNameKana,
			@RequestParam (name = "firstNameKana",defaultValue = "") String firstNameKana,
			@RequestParam (name = "address",defaultValue = "") String address,
			@RequestParam (name = "tel",defaultValue = "") String tel,
			@RequestParam (name = "email",defaultValue = "") String email,
			@RequestParam (name = "password",defaultValue = "") String password,
			Model model){
		
		User user = userRepository.findById(id).get();
		
//		エラー処理
		List<String> errorList = new ArrayList<>();
		
		if(lastName.isEmpty() || firstName.isEmpty())
		errorList.add("名前は必須です");	
		
		if(lastNameKana.isEmpty() || firstNameKana.isEmpty()) {
			errorList.add("フリガナは必須です");
			}
		if(address.isEmpty()) {
			errorList.add("住所は必須です");
			}
		if(tel.isEmpty()) {
			errorList.add("電話番号は必須です");
		}
		if(email.isEmpty()) {
			errorList.add("メールアドレスは必須です");
		}else if(!email.contains("@")) { //指定した値が入っているか
		    errorList.add("正しいメールアドレスを入力してください（@が必要です）");
		}
		
		if(password.isEmpty()) {
			errorList.add("パスワードは必須です");
		} else if (6 > password.length() || password.length() > 16)
			errorList.add("パスワードは6文字以上16文字以下にしてください");
		
		if(errorList.size()>0) {
			model.addAttribute("message",errorList);
			
			return "userEdit";
		}
		user.setLastName(lastName);
		user.setFirstName(firstName);
		user.setLastNameKana(lastNameKana);
		user.setFirstNamekana(firstNameKana);
		user.setAddress(address);
		user.setTel(tel);
		user.setEmail(email);
		user.setPassword(password);
	
		userRepository.save(user);
	
	return"redirect:/mypage";
	
	}
	
}
