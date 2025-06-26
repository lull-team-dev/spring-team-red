package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.constant.PrefLabel;
import com.example.demo.entity.Hotel;
import com.example.demo.entity.Plan;
import com.example.demo.repository.HotelRepository;

@Controller
public class HotelController {
	
	@Autowired
	HotelRepository hotelRepository;
	
	// ホテル検索画面（TOP）を表示
	@GetMapping("/hotel")
	public String index(Model model) {
		// enumを配列取得して(values)、配列→リストへ変換(Arrays.asList)
		model.addAttribute("prefLabel", Arrays.asList(PrefLabel.values()));
		
		return "hotel";
	}
	
	// ホテル検索機能
	@GetMapping("/hotel/search")
	public String search(
			@RequestParam (defaultValue = "")String pref,
			@RequestParam LocalDate checkIn,
			@RequestParam LocalDate checkOut,
			@RequestParam (defaultValue = "")Integer numberOfPeople,
			@RequestParam (defaultValue = "")String priceRange,
			@RequestParam (defaultValue = "")String keyword,
			Model model) {
		
		if(checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
			model.addAttribute("errorMsg","チェックアウト日はチェックイン日より後に設定してください。");
			model.addAttribute("hotelList", new ArrayList<>());
			// enumを配列取得して(values)、配列→リストへ変換(Arrays.asList)
			model.addAttribute("prefLabel", Arrays.asList(PrefLabel.values()));
			model.addAttribute("pref", pref);
			model.addAttribute("checkIn", checkIn);
			model.addAttribute("checkOut", checkOut);
			model.addAttribute("numberOfPeople", numberOfPeople);
			model.addAttribute("priceRange", priceRange);
			model.addAttribute("keyword", keyword);
			return "hotel";
		}
		// enumを配列取得して(values)、配列→リストへ変換(Arrays.asList)
		model.addAttribute("prefLabel", Arrays.asList(PrefLabel.values()));
		model.addAttribute("pref", pref);
		model.addAttribute("checkIn", checkIn);
		model.addAttribute("checkOut", checkOut);
		model.addAttribute("numberOfPeople", numberOfPeople);
		model.addAttribute("priceRange", priceRange);
		model.addAttribute("keyword", keyword);
		
		// 価格検索用フィールド
		Integer minPrice = null;
		Integer maxPrice = null;
		
		// SQL用に価格配列priceRange → minPrice & maxPriceに変換
		// containsは、引数に指定された文字列のみに使用
		// splitは、文字列で指定した特定の値を堺に、分割させて別々の値として扱える
		// parseIntは、文字列を整数に変換(Integerクラスのメソッド)
		if(priceRange != null && !priceRange.isEmpty() && priceRange.contains("-")) {
			String[] range = priceRange.split("-");
			minPrice = Integer.parseInt(range[0]);
			maxPrice = Integer.parseInt(range[1]);
		}
		
		// ホテル一覧表示用の値をデータベースから取得
		List<Hotel> hotelList = new ArrayList<>();
		hotelList = hotelRepository.findByhotelList(pref,minPrice,maxPrice,keyword);
		
		
		// hotelListから、<Plan>plansリストを経由して、最安値(minPrice)を取得する。
		for(Hotel hotel : hotelList) {
			List<Plan> plans = hotel.getPlans(); // hotelにあるplansフィールドを、Plan型のplans変数に格納。
			
			if(plans != null && !plans.isEmpty()) { // plans変数に値が格納されているか確認。
				
				// Java Streamは、リストを加工しやすくするJava8の仕組み。
				// 後のmap()メソッドでInteger型に変換されているため、Integer型のmin変数として定義。
				Integer min = plans.stream()
						
						// .map(plan -> plan.getPrice())と同じ。
						// Plan型のplansを、plan.getPriceによって「PlanEntityのgetPrice()メソッド」を呼び出すことでInteger型に変換している。
						.map(Plan::getPrice)
						
						// min()メソッドは、Streamした中で最小の値を渡す。
						// →特性として、最小値が「見つかればその値を返し、見つからなければ空っぽのOptionalを返す」
						// compareToメソッドは、Java標準の比較メソッドで、Integer型の比較をしている。
						// ※min()だけでは比較できないから比較メソッドとして必要。
						.min(Integer::compareTo)
						
						// min()メソッドの特性により、nullだった場合に「Optional」で返される。
						// .orElse(value)は、Optionalに値があればそれを返し、なければvalueを返すOptionalクラスのメソッド。
						// 今回はnullだった場合にnullで返す処理。
						.orElse(null);
				
				hotel.setPrice(min); // hotelListが入ったhotel変数に、取得してきた最安値をセット。※HotelEntityのセッター。
			}
		}
		
		
		model.addAttribute("hotelList",hotelList);
		
		return "hotel";
	}
	
	
}
