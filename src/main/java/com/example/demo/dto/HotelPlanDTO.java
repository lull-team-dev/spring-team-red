package com.example.demo.dto;

public class HotelPlanDTO {
	private Integer id;
	private String name; // ホテル名
	private String photo;
	private String prefecture;
	private String city;
	private String planName;
	private Integer price;
	
	
	// デフォルトコンストラクタ
	public HotelPlanDTO() {
		super();
	}
	
	public HotelPlanDTO(Integer id, String name, String photo, String prefecture, String city, String planName,Integer price) {
		super();
		this.id = id;
		this.name = name;
		this.photo = photo;
		this.prefecture = prefecture;
		this.city = city;
		this.planName = planName;
		this.price = price;
	}

	
	 // ゲッターセッター
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getPrefecture() {
		return prefecture;
	}

	public void setPrefecture(String prefecture) {
		this.prefecture = prefecture;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
	
}
