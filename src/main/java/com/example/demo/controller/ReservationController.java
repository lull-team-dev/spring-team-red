package com.example.demo.controller;

import java.util.Date;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Hotel;
import com.example.demo.entity.Plan;
import com.example.demo.entity.User;
import com.example.demo.model.Account;
import com.example.demo.repository.PlanRepository;
import com.example.demo.repository.ReservationRepository;

@Controller
public class ReservationController {
	
	@Autowired
	ReservationRepository reservationRepository;
	
	@Autowired
	PlanRepository planRepository;
	
	@Autowired
	HttpSession session;
	
	
	//stayInfoの表示
	@GetMapping("/stayInfo/{planId}")
	public String stayInfo(
			@PathVariable("planId") Integer planId,
			@RequestParam("checkIn") Date checkIn,
			@RequestParam("checkOut") Date checkOut,
			@RequestParam("numberOfPeople") Integer numberOfPeople,
			Model model) {
		
		//セッションからユーザー情報を取得
		Account account = (Account) session.getAttribute("user");
		
		//ログインしてない場合はログイン画面に遷移
		if (account == null) {
			model.addAttribute("error", "notLoggedIn");
			return "redirect:/";
		}
		
		//URLからplanIdを取得
		Plan plan = planRepository.findById(planId).orElse(null);
		
		//ホテル情報の取得
		Hotel hotel = plan.getHotel();
		
		//stayInfoに情報を渡す
		model.addAttribute("plan", plan);
		model.addAttribute("hotel", hotel);
		model.addAttribute("checkIn", checkIn);
		model.addAttribute("checkOut", checkOut);
		model.addAttribute("numberOfPeople", numberOfPeople);
		
		return "stayInfo";
	}
	
	@PostMapping("/stayInfo/confirm")
	public String stayInfoConfirm(
			@RequestParam("planId") Integer planId,
			@RequestParam("name") String name,
			@RequestParam("kana") String kana,
			@RequestParam("address") String address,
			@RequestParam("tel") String tel,
			@RequestParam("email") String email,
			@RequestParam("pay") String pay,
			Model model) {
		
		//プラン情報の取得
		Plan plan = planRepository.findById(planId).orElse(null);
		
		//ホテル情報の取得
		Hotel hotel = plan.getHotel();
		
		//確認画面で情報を表示するためにUserをオブジェクト化
		User user = new User();
		user.setLastName(name.split(" ")[0]);
		//氏名が2つ以上の単語で構成されている場合は「名」を取りだす
		user.setFirstName(name.split(" ").length > 1 ? name.split(" ")[1] : "");
		user.setLastNameKana(kana.split(" ")[0]);
		user.setFirstNameKana(kana.split(" ").length > 1 ? name.split(" ")[1] : "");
		user.setAddress(address);
		user.setTel(tel);
		user.setEmail(email);
		
		//modelでHTMLに情報を渡す
		model.addAttribute("plan", plan);
		model.addAttribute("hotel", hotel);
		model.addAttribute("user", user);
		model.addAttribute("pay", pay);
		
		return "confirmStayInfo";
	}
}
