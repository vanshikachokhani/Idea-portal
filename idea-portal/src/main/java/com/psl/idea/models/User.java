package com.psl.idea.models;

public class User {
	private String emailId;
	private String password;
	
	public User(String emailId, String password) {
		super();
		this.emailId = emailId;
		this.password = password;
	}
	public User() {
		// TODO Auto-generated constructor stub
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
