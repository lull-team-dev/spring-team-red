package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HotelController {
	
	// ホテル検索画面（TOP）を表示
	@GetMapping("/hotel")
	public String index() {
		return "hotel";
	}

	
}
