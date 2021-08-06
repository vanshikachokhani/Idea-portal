package com.psl.idea.models;

public class UpdateUser {
	private String emailId;
	private String oldpassword;
	private String newpassword;
	
	public UpdateUser() {
		super();
	}
	
	public UpdateUser(String emailId, String oldpassword, String newpassword) {
		super();
		this.emailId = emailId;
		this.oldpassword = oldpassword;
		this.newpassword = newpassword;
	}

	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getOldpassword() {
		return oldpassword;
	}
	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	
	
}
