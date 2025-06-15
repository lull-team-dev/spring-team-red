package com.example.demo.controller;

import java.util.Date;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Hotel;
import com.example.demo.entity.Plan;
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
}
