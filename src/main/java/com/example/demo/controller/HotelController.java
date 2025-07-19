package com.example.demo.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.constant.PrefLabel;
import com.example.demo.dto.HotelPlanDTO;
import com.example.demo.entity.Hotel;
import com.example.demo.entity.Plan;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.PlanRepository;

@Controller
public class HotelController {
	
	@Autowired
	HotelRepository hotelRepository;
	
	@Autowired
	PlanRepository planRepository;
	
	// ホテル検索画面（TOP）を表示
	@GetMapping({"/" ,"/hotel"})
	public String index(Model model) {
		
		// enumを配列取得して(values)、配列→リストへ変換(Arrays.asList)
		model.addAttribute("prefLabel", Arrays.asList(PrefLabel.values()));
		
		// 明日の日付をチェックインに渡す
		LocalDate tomorrow = LocalDate.now().plusDays(1);
		model.addAttribute("tomorrow", tomorrow.toString());
		
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
			@RequestParam (defaultValue = "1")int page,
			@RequestParam (defaultValue = "none")String sort,
			Model model) {
		
		
		// チェックイン日が今日の場合
		if(checkIn.isBefore(LocalDate.now().plusDays(1))) {
			model.addAttribute("errorMsg","チェックイン日は明日以降を設定してください。");
			// enumを配列取得して(values)、配列→リストへ変換(Arrays.asList)
			model.addAttribute("prefLabel", Arrays.asList(PrefLabel.values()));
			model.addAttribute("pref", pref);
			model.addAttribute("checkIn", checkIn);
			model.addAttribute("checkOut", checkOut);
			model.addAttribute("numberOfPeople", numberOfPeople);
			model.addAttribute("priceRange", priceRange);
			model.addAttribute("keyword", keyword);
			model.addAttribute("page", page);
			model.addAttribute("sort", sort);
			return "hotel";
		}
		
		// チェックイン日とチェックアウト日が一緒の場合
		if(checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
			model.addAttribute("errorMsg","チェックアウト日はチェックイン日より後に設定してください。");
			// enumを配列取得して(values)、配列→リストへ変換(Arrays.asList)
			model.addAttribute("prefLabel", Arrays.asList(PrefLabel.values()));
			model.addAttribute("pref", pref);
			model.addAttribute("checkIn", checkIn);
			model.addAttribute("checkOut", checkOut);
			model.addAttribute("numberOfPeople", numberOfPeople);
			model.addAttribute("priceRange", priceRange);
			model.addAttribute("keyword", keyword);
			model.addAttribute("page", page);
			model.addAttribute("sort", sort);
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
		model.addAttribute("page", page);
		model.addAttribute("sort", sort);
		
		
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
		
		
		// ページネーション
		int limit = 20;
		int offset = (page - 1) * limit; //2ページ目なら、21件目から20件(limit)取得
		
		
		// ホテル一覧表示用の値をデータベースから取得
		List<Object[]> rawList = new ArrayList<>();
		
		// ソート
		switch(sort) {
		case "asc": //昇順
			rawList = hotelRepository.findHotelListOrderByPriceAsc(pref,minPrice,maxPrice,keyword,limit,offset);
			break;
		case "desc": // 降順
			rawList = hotelRepository.findHotelListOrderByPriceDesc(pref,minPrice,maxPrice,keyword,limit,offset);
			break;
		default: // 通常
			rawList = hotelRepository.findHotelList(pref,minPrice,maxPrice,keyword,limit,offset);
			break;
		}
		
		
		// Object[]をDTOに変換 (順序はSQLのSELECTと一緒)
		// rawList.stream()：リストを1件ずつ順番に処理する「Stream」を開始
		// .map(record -> new HotelPlanDTO：各要素（ここでは Object[] record）を別の型（DTO）に変換
		// .toList()：最終的にListにまとめて変換を完了
		List<HotelPlanDTO> hotelList = rawList.stream()
				.map(record -> new HotelPlanDTO(
						(Integer) record[0], // ht.id
						(String) record[1], // ht.name
						(String) record[2], // ht.photo
						(String) record[3], // ht.prefecture
						(String) record[4], // ht.city
						(String) record[5], // pl.planName
						(Integer) record[6] // pl.price
								))
				.toList();
		
		// ページ数取得
		// Mathクラスは、数学に関する便利な関数をまとめたもの
		// ceil(x)は、xを切り上げる関数(double) ※(double) をつけることで浮動小数点で計算
		// 三項演算子（条件演算子）で0件対策 → 条件式 ? 真のときの値 : 偽のときの値;
		int totalCount = hotelRepository.countByHotelList(pref, minPrice, maxPrice, keyword);
		int totalPages = totalCount == 0 ? 1 : (int) Math.ceil((double)totalCount / limit);
		
		
//		// hotelListから、<Plan>plansリストを経由して、最安値(minPrice)を取得する。
//		for(Hotel hotel : hotelList) {
//			List<Plan> plans = hotel.getPlans(); // hotelにあるplansフィールドを、Plan型のplans変数に格納。
//			
//			if(plans != null && !plans.isEmpty()) { // plans変数に値が格納されているか確認。
//				
//				// Java Streamは、リストを加工しやすくするJava8の仕組み。
//				// 後のmap()メソッドでInteger型に変換されているため、Integer型のmin変数として定義。
//				Integer min = plans.stream()
//						
//						// .map(plan -> plan.getPrice())と同じ。
//						// Plan型のplansを、plan.getPriceによって「PlanEntityのgetPrice()メソッド」を呼び出すことでInteger型に変換している。
//						.map(Plan::getPrice)
//						
//						// min()メソッドは、Streamした中で最小の値を渡す。
//						// →特性として、最小値が「見つかればその値を返し、見つからなければ空っぽのOptionalを返す」
//						// compareToメソッドは、Java標準の比較メソッドで、Integer型の比較をしている。
//						// ※min()だけでは比較できないから比較メソッドとして必要。
//						.min(Integer::compareTo)
//						
//						// min()メソッドの特性により、nullだった場合に「Optional」で返される。
//						// .orElse(value)は、Optionalに値があればそれを返し、なければvalueを返すOptionalクラスのメソッド。
//						// 今回はnullだった場合にnullで返す処理。
//						.orElse(null);
//				
//				hotel.setPrice(min); // hotelListが入ったhotel変数に、取得してきた最安値をセット。※HotelEntityのセッター。
//			}
//		}
		
		List<Object> pageItems = generatePaginationList(totalPages, page);

		
		
		model.addAttribute("hotelList",hotelList);
		model.addAttribute("sort",sort);
		model.addAttribute("totalPages",totalPages);
		model.addAttribute("pageItems", generatePaginationList(totalPages, page));
		model.addAttribute("currentPage",page);
		
		return "hotel";
	}
	
	private List<Object> generatePaginationList(int totalPages, int currentPage) {
	    List<Object> pages = new ArrayList<>();

	    if (totalPages <= 7) {
	        // ページ数が少ないなら全表示
	        for (int i = 1; i <= totalPages; i++) {
	            pages.add(i);
	        }
	    } else {
	        pages.add(1); // 最初のページは必ず表示

	        if (currentPage > 4) {
	            pages.add("..."); // 現在ページが離れていれば省略
	        }

	        // 中央のページ範囲
	        int start = Math.max(2, currentPage - 1);
	        int end = Math.min(totalPages - 1, currentPage + 1);

	        for (int i = start; i <= end; i++) {
	            pages.add(i);
	        }

	        if (currentPage < totalPages - 3) {
	            pages.add("...");
	        }

	        pages.add(totalPages); // 最後のページは必ず表示
	    }

	    return pages;
	}
	
	
	
	// ホテル詳細表示
	@PostMapping("hotel/detail/{id}")
	public String hotelDetail(
			@PathVariable Integer id,
			@RequestParam (defaultValue = "")String pref,
			@RequestParam LocalDate checkIn,
			@RequestParam LocalDate checkOut,
			@RequestParam (defaultValue = "")Integer numberOfPeople,
			@RequestParam (defaultValue = "")String priceRange,
			@RequestParam (defaultValue = "")String keyword,
			@RequestParam (defaultValue = "1")int page,
			@RequestParam (defaultValue = "none")String sort,
			Model model) {
		
		// ホテル情報の取得
		Optional<Hotel> optionalHotel = hotelRepository.findById(id);
		
		// idでプランの検索
		List<Plan> planList = planRepository.findPlanByHotelId(id);
		
		// ホテルとプランの取得確認
		if(planList == null || planList.isEmpty() || optionalHotel == null || optionalHotel.isEmpty()) {
			// enumを配列取得して(values)、配列→リストへ変換(Arrays.asList)
			model.addAttribute("prefLabel", Arrays.asList(PrefLabel.values()));
			model.addAttribute("pref", pref);
			model.addAttribute("checkIn", checkIn);
			model.addAttribute("checkOut", checkOut);
			model.addAttribute("numberOfPeople", numberOfPeople);
			model.addAttribute("priceRange", priceRange);
			model.addAttribute("keyword", keyword);
			model.addAttribute("page", page);
			model.addAttribute("sort", sort);
			
			model.addAttribute("error", "ページが存在しません。");
			
			return "detailHotel";
		}
		
		// 滞在日数(stay)の計算
		long stay = ChronoUnit.DAYS.between(checkIn, checkOut);
		
		Hotel hotel = optionalHotel.get();
		
		model.addAttribute("hotel", hotel);
		model.addAttribute("planList", planList);
		model.addAttribute("pageTitle", hotel.getName());
		model.addAttribute("stay", stay);
		
		model.addAttribute("pref", pref);
		model.addAttribute("checkIn", checkIn);
		model.addAttribute("checkOut", checkOut);
		model.addAttribute("numberOfPeople", numberOfPeople);
		model.addAttribute("priceRange", priceRange);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", page);
		model.addAttribute("sort", sort);
		
		return "detailHotel";
	}
	
}
