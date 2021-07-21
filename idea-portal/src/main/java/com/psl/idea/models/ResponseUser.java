package com.psl.idea.models;

public class ResponseUser {
	private String name;
	private String emailId;
	private String privilege;

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
	public ResponseUser(String name, String emailId, String privilege) {
		super();
		this.name = name;
		this.emailId = emailId;
		this.privilege = privilege;
	}
	
	
}
