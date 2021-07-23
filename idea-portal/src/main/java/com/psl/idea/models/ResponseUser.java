package com.psl.idea.models;

public class ResponseUser {
	private long userId;
	private String name;
	private String emailId;
	private String privilege;
	private String company;
	public ResponseUser(long userId, String name, String emailId, String privilege, String company) {
		super();
		this.userId = userId;
		this.name = name;
		this.emailId = emailId;
		this.privilege = privilege;
		this.company = company;
	}
	
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPrivilege() {
		return privilege;
	}
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	
	
	
}
