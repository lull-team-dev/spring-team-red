package com.example.demo.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import com.example.demo.validator.FieldsValueMatch;

@Entity
@Table(name = "users") //usersテーブルを指定
@FieldsValueMatch(field = "password", fieldMatch = "confirmPassword", message = "パスワードと確認が一致しません")
public class User {
	//フィールド
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //ユーザーID

	@NotBlank
	private String lastName; //苗字

	@NotBlank
	private String firstName; //名前

	@NotBlank
	private String lastNameKana;

	@NotBlank
	private String firstNameKana;

	@NotBlank
	private String address; //住所

	@NotBlank
	@Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}")
	private String tel; //電話番号

	@NotBlank
	@Email
	private String email; //メールアドレス(ログインID)

	@NotBlank
	private String password; //パスワード

	@Transient
	private String confirmPassword; //パスワード確認用(DBに保存されない（確認用フィールド）)

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Reservation> reservations;

	//コンストラクタ

	public User() {
	}

	public User(String firstName, String lastName, String firstNameKana, String lastNameKana, String address,
			String tel, String email, String password, String confirmPassword) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.firstNameKana = firstNameKana;
		this.lastNameKana = lastNameKana;
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

	//パスワード確認用
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
