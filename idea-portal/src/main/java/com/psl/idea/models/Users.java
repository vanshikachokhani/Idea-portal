package com.psl.idea.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Table(name="users")
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private long userId;

	@Column(nullable = false, unique = true, name = "USER_NAME")
	private String name;
	
	@Column(nullable = false, unique = true, name = "PHONE_NUMBER")
	private long phoneNumber;
	
	@Column(nullable = false, unique = true, name = "EMAIL_ID")
	private String emailId;
	
	@Column(nullable = false, name = "PASSWORD")
	private String password;

	@Column(nullable = false)
	@ManyToOne
	@JoinColumn(name="PRIVILEGE_ID")
	private Privilege privilege;

	public long getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Privilege getPrivilege() {
		return privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}

	@Override
	public String toString() {
		return "Users [userId=" + userId + ", name=" + name + ", phoneNumber=" + phoneNumber + ", emailId=" + emailId
				+ ", password=" + password + ", privilege=" + privilege + "]";
	}
	
	
}
