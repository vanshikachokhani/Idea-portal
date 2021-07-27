package com.psl.idea.models;

public class UpdateUserEmail {
	private String password;
	private String oldemailId;
	private String newemailId;
	private String oldcompany;
	private String newcompany;
	
	public String getOldcompany() {
		return oldcompany;
	}
	public void setOldcompany(String oldcompany) {
		this.oldcompany = oldcompany;
	}
	public String getNewcompany() {
		return newcompany;
	}
	public void setNewcompany(String newcompany) {
		this.newcompany = newcompany;
	}
	public String getOldemailId() {
		return oldemailId;
	}
	public void setOldemailId(String oldemailId) {
		this.oldemailId = oldemailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNewemailId() {
		return newemailId;
	}
	public void setNewemailId(String newemailId) {
		this.newemailId = newemailId;
	}
	
	
}
