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
import jakarta.persistence.Transient;

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

	@Transient
	private String confirmPassword; //パスワード確認用(DBに保存されない（確認用フィールド）)

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Reservation> reservations;

	//コンストラクタ

	public User() {
	}

	public User(String firstName, String lastName , String address, String lastNameKana, String firstNameKana,  String tel, String email, String password, String confirmPassword) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.firstName = firstNameKana;
		this.lastName = lastNameKana;
		this.address = address;
		this.tel = tel;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
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
	
	
	//苗字
	public String getLastNameKana() {
		return lastName;
	}
	
	public void setLastNameKana(String lastNameKana) {
		this.lastName = lastNameKana;
	}
	
	//名前
	public String getFirstNameKana() {
		return firstNameKana;
	}
	
	public void setFirstNamekana(String firstNameKana) {
		this.firstName = firstNameKana;
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

	//パスワード確認用
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setContirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
