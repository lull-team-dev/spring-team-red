package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

	private String confirmPassword; //パスワード確認用

	//コンストラクタ

	public User() {
	}

	public User(String name, String address, String tel, String email, String password) {
		this.name = name;
		this.address = address;
		this.tel = tel;
		this.email = email;
		this.password = password;
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

}
