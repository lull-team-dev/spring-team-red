package com.example.demo.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users") //usersテーブルを指定
public class User {
	//フィールド
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //ユーザーID

	@Column(name = "last_name")
	private String lastName; //苗字

	@Column(name = "first_name")
	private String firstName; //名前

	@Column(name = "last_name_kana")
	private String lastNameKana;

	@Column(name = "first_name_kana")
	private String firstNameKana;

	private String address; //住所

	private String tel; //電話番号

	private String email; //メールアドレス(ログインID)

	private String password; //パスワード

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Reservation> reservations;

	//コンストラクタ

	public User() {
	}

	public User(String firstName, String lastName, String firstNameKana, String lastNameKana, String address,
			String tel, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.firstNameKana = firstNameKana;
		this.lastNameKana = lastNameKana;
		this.address = address;
		this.tel = tel;
		this.email = email;
		this.password = password;
	}

	//ゲッター
	//セッター

	//id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	//苗字
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	//名前
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	//みょうじ
	public String getLastNameKana() {
		return lastNameKana;
	}

	public void setLastNameKana(String lastNameKana) {
		this.lastNameKana = lastNameKana;
	}

	//なまえ
	public String getFirstNameKana() {
		return firstNameKana;
	}

	public void setFirstNameKana(String firstNameKana) {
		this.firstNameKana = firstNameKana;
	}

	//住所
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	//電話番号
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	//メールアドレス
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	//パスワード
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
