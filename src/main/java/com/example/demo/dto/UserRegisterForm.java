package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRegisterForm {

	@NotBlank(message = "姓を入力してください")
	private String lastName;

	@NotBlank(message = "名を入力してください")
	private String firstName;

	@NotBlank(message = "セイを入力してください")
	private String lastNameKana;

	@NotBlank(message = "メイを入力してください")
	private String firstNameKana;

	@NotBlank(message = "住所を入力してください")
	private String address;

	@NotBlank(message = "電話番号を入力してください")
	@Pattern(regexp = "^[0-9\\-]+$", message = "電話番号の形式が不正です")
	private String tel;

	@NotBlank(message = "メールアドレスを入力してください")
	@Email(message = "メールアドレスの形式が不正です")
	private String email;

	@NotBlank(message = "パスワードを入力してください")
	@Size(min = 6, message = "パスワードは6文字以上で入力してください")
	private String password;

	@NotBlank(message = "パスワード（確認）を入力してください")
	private String passConfirm;

	// --- Getter / Setter ---

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastNameKana() {
		return lastNameKana;
	}

	public void setLastNameKana(String lastNameKana) {
		this.lastNameKana = lastNameKana;
	}

	public String getFirstNameKana() {
		return firstNameKana;
	}

	public void setFirstNameKana(String firstNameKana) {
		this.firstNameKana = firstNameKana;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassConfirm() {
		return passConfirm;
	}

	public void setPassConfirm(String passConfirm) {
		this.passConfirm = passConfirm;
	}
}
