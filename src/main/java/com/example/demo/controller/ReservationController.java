package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

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
import com.example.demo.entity.Reservation;
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
			@RequestParam(name = "checkIn", defaultValue = "2025-01-01") LocalDate checkIn,
			@RequestParam(name = "checkOut", defaultValue = "2025-01-02") LocalDate checkOut,
			@RequestParam(name = "stay", defaultValue = "1") Integer stay,
			@RequestParam(name = "numberOfPeople", defaultValue = "2") Integer numberOfPeople,
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
		
		//合計金額の計算
		int planPrice = plan.getPrice();
		long totalPrice = planPrice * stay * numberOfPeople;
		
		//stayInfoに情報を渡す
		model.addAttribute("user", account);
		model.addAttribute("plan", plan);
		model.addAttribute("hotel", hotel);
		model.addAttribute("checkIn", checkIn);
		model.addAttribute("checkOut", checkOut);
		model.addAttribute("numberOfPeople", numberOfPeople);
		model.addAttribute("totalPrice", totalPrice);
		
		return "stayInfo";
	}
	
	
	@PostMapping("/stayInfo/confirm")
	public String stayInfoConfirm(
			@RequestParam("planId") Integer planId,
			@RequestParam("totalPrice") Integer totalPrice,
			@RequestParam("checkIn") LocalDate checkIn,
			@RequestParam("checkOut") LocalDate checkOut,
			@RequestParam("numberOfPeople") Integer numberOfPeople,
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
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("checkIn", checkIn);
		model.addAttribute("checkOut", checkOut);
		model.addAttribute("numberOfPeople", numberOfPeople);
		
		return "confirmStayInfo";
	}
	
	
	@PostMapping("/stayInfo/complete")
	public String stayInfoComplete(
			@RequestParam("planId") Integer planId,
			@RequestParam("numberOfPeople") Integer numberOfPeople,
			@RequestParam("checkIn") LocalDate checkIn,
			@RequestParam("checkOut") LocalDate checkOut,
			@RequestParam("totalPrice") Integer totalPrice,
			@RequestParam("pay") String pay,
			Model model) {
		
		//セッションからログインユーザーの情報を取得
		Account account = (Account) session.getAttribute("user");
		
		//DBに保存されているユーザー情報を取得
		User user = account.getUser();
		
		//パラメータのplanIdでPlanテーブルから情報を取得
		Plan plan = planRepository.findById(planId).orElse(null);
		
		//Reservationエンティティに定義されているフィールドに値をセットする
		Reservation reservation = new Reservation();
		reservation.setUser(user);
		reservation.setPlan(plan);
		reservation.setNumberOfPeople(numberOfPeople);
		reservation.setCheckIn(checkIn);
		reservation.setCheckOut(checkOut);
		reservation.setTotalPrice(totalPrice);
		reservation.setPay(pay);
		
		//DBに保存
		reservationRepository.save(reservation);
		
		return "completeStayInfo";
		
		
	}
	
	
	@GetMapping("/bookingList")
	public String index(Model model) {
		
		//セッションからユーザー情報を取得
		Account account = (Account) session.getAttribute("user");
		
		//取得したユーザー情報からID情報を取得
		User user = account.getUser();
		
		//ログイン中のユーザーだけの予約一覧を取得
		List<Reservation> reservations = reservationRepository.findByUserIdFetchPlanAndHotel(user.getId());
		
		//HTMLに値を渡す
		model.addAttribute("reservations", reservations);
		
		return "bookingList";
	}
}
