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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users") //usersテーブルを指定
public class User {
	//フィールド
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //ユーザーID

	@NotBlank(message = "姓を入力してください")
	@Column(name = "last_name")
	private String lastName; //苗字

	@NotBlank(message = "名を入力してください")
	@Column(name = "first_name")
	private String firstName; //名前

	@NotBlank(message = "姓のフリガナを入力してください")
	@Column(name = "last_name_kana")
	private String lastNameKana;

	@NotBlank(message = "名のフリガナを入力してください")
	@Column(name = "first_name_kana")
	private String firstNameKana;

	@NotBlank(message = "住所を入力してください")
	private String address; //住所

	@NotBlank(message = "電話番号を入力してください")
	@Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}")
	private String tel; //電話番号

	@NotBlank(message = "メールアドレスを入力してください")
	@Email(message = "メールアドレスの形式")
	private String email; //メールアドレス(ログインID)

	@NotBlank(message = "パスワードを入力してください")
	@Size(min = 8, max = 20, message = "8文字以上,20文字以内で入力してください")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "英数字のみで入力してください")
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
