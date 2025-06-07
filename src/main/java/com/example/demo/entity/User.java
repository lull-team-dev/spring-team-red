package com.example.demo.entity;

import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "users") //usersテーブルを指定
public class User {
	//フィールド
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //ユーザーID

	private String name; //氏名

	private String address; //住所

	private String tel; //電話番号

	private String email; //メールアドレス(ログインID)

	private String password; //パスワード

	@Transient
	private String confirmPassword; //パスワード確認用(DBに保存されない（確認用フィールド）)

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Booking> posts = new ArrayList<>();

	//コンストラクタ

	public User() {
	}

	public User(String name, String address, String tel, String email, String password, String confirmPassword) {
		this.name = name;
		this.address = address;
		this.tel = tel;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

	//ゲッター
	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getTel() {
		return tel;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	//セッター
	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setContirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
