package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
//import lombok.Data;

//@Data
@Entity
@Table(name = "user")
public class UserDtls {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String fullName;

	private String email;
	
	private String password;
	
	private String role;
	
	private String mobileNumber;

	private String verificationCode;
	
	private boolean enable;
	
	public boolean isEnable() {
	return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

		public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	
	
	public UserDtls(String fullName, String email, String password, String role, String mobileNumber, String verificationCode, boolean enable) {
		super();
		this.fullName = fullName;
		this.email = email;
		this.password = password;
	    this.role = role;
		this.mobileNumber = mobileNumber;
		this.verificationCode = verificationCode;
		this.enable = enable;
	}

	public UserDtls() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}