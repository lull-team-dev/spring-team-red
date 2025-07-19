package com.example.demo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Hotel;
import com.example.demo.entity.Plan;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.User;
import com.example.demo.model.Account;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.PlanRepository;
import com.example.demo.repository.ReservationRepository;

@Controller
public class ReservationController {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    PlanRepository planRepository;

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    HttpSession session;

    @PostMapping("/stayInfo/{planId}")
    public String stayInfo(
            @PathVariable("planId") Integer planId,
            @RequestParam(name = "checkIn", defaultValue = "2025-01-01") LocalDate checkIn,
            @RequestParam(name = "checkOut", defaultValue = "2025-01-02") LocalDate checkOut,
            @RequestParam(name = "stay", defaultValue = "1") Integer stay,
            @RequestParam(name = "numberOfPeople", defaultValue = "2") Integer numberOfPeople,
            Model model) {

        Account account = (Account) session.getAttribute("user");
        if (account == null) {
        	model.addAttribute("error", "ログインしてください");
            return "login";
        }

        Plan plan = planRepository.findById(planId).orElse(null);
        Hotel hotel = plan.getHotel();
        int planPrice = plan.getPrice();
        long totalPrice = planPrice * stay * numberOfPeople;

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

        Account account = (Account) session.getAttribute("user");
        if (account == null) {
            return "sessionExpired";
        }

        Plan plan = planRepository.findById(planId).orElse(null);
        Hotel hotel = plan.getHotel();

        User user = new User();
        user.setLastName(name.split(" ")[0]);
        user.setFirstName(name.split(" ").length > 1 ? name.split(" ")[1] : "");
        user.setLastNameKana(kana.split(" ")[0]);
        user.setFirstNameKana(kana.split(" ").length > 1 ? kana.split(" ")[1] : "");
        user.setAddress(address);
        user.setTel(tel);
        user.setEmail(email);

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
            @RequestParam("hotelId") Integer hotelId,
            @RequestParam("numberOfPeople") Integer numberOfPeople,
            @RequestParam("checkIn") LocalDate checkIn,
            @RequestParam("checkOut") LocalDate checkOut,
            @RequestParam("totalPrice") Integer totalPrice,
            @RequestParam("pay") String pay,
            Model model) {

        Account account = (Account) session.getAttribute("user");
        if (account == null) {
            return "sessionExpired";
        }

        User user = account.getUser();
        Plan plan = planRepository.findById(planId).orElse(null);
        Hotel hotel = hotelRepository.findById(hotelId).orElse(null);

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setPlan(plan);
        reservation.setNumberOfPeople(numberOfPeople);
        reservation.setCheckIn(checkIn);
        reservation.setCheckOut(checkOut);
        reservation.setTotalPrice(totalPrice);
        reservation.setPay(pay);

        reservationRepository.save(reservation);

        model.addAttribute("hotel", hotel);
        return "completeStayInfo";
    }

    @GetMapping("/bookingList")
    public String index(Model model) {

        Account account = (Account) session.getAttribute("user");
        if (account == null) {
            return "sessionExpired";
        }

        User user = account.getUser();
        List<Reservation> reservations = reservationRepository.findByUserIdFetchPlanAndHotel(user.getId());
        model.addAttribute("reservations", reservations);
        return "bookingList";
    }

    @PostMapping("/bookingList/cancel")
    @ResponseBody
    public Map<String, Object> cancelReservation(@RequestBody Map<String, Object> payload) {
        Map<String, Object> response = new HashMap<>();

        Account account = (Account) session.getAttribute("user");
        if (account == null) {
            response.put("success", false);
            response.put("message", "セッションが切れています。再ログインしてください。");
            return response;
        }

        try {
            Integer reservationId = Integer.parseInt(payload.get("reservationId").toString());
            Optional<Reservation> optional = reservationRepository.findById(reservationId);

            if (optional.isPresent()) {
                Reservation reservation = optional.get();
                if (!reservation.isCancelled() && !reservation.getCheckIn().isBefore(LocalDate.now())) {
                    reservation.setCancelled(true);
                    reservationRepository.save(reservation);
                    response.put("success", true);
                } else {
                    response.put("success", false);
                    response.put("message", "キャンセル対象外の予約です");
                }
            } else {
                response.put("success", false);
                response.put("message", "予約が見つかりません");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "エラーが発生しました：" + e.getMessage());
        }

        return response;
    }

    @GetMapping("/reservation/{id}")
    public String showReservationDetail(@PathVariable("id") Integer reservationId, Model model) {

        Account account = (Account) session.getAttribute("user");
        if (account == null) {
            return "sessionExpired";
        }

        Optional<Reservation> reservationOpt = reservationRepository.findById(reservationId);
        Reservation reservation = reservationOpt.get();

        Optional<Plan> planOpt = planRepository.findById(reservation.getPlan().getId());
        Plan plan = planOpt.get();

        Optional<Hotel> hotelOpt = hotelRepository.findById(plan.getHotel().getId());
        Hotel hotel = hotelOpt.get();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String createdAt = reservation.getCreatedAt().format(formatter);
        String checkIn = reservation.getCheckIn().format(formatter);
        String checkOut = reservation.getCheckOut().format(formatter);

        model.addAttribute("reservation", reservation);
        model.addAttribute("plan", plan);
        model.addAttribute("hotel", hotel);
        model.addAttribute("createdAt", createdAt);
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);

        return "reservationDetail";
    }
}
