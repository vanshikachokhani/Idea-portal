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
	@Column(name = "user_id")
	private long userId;

	@Column(nullable = false, name = "name")
	private String name;
	
	@Column(nullable = false, unique = true, name = "phone_number")
	private long phoneNumber;
	
	@Column(nullable = false, unique = true, name = "email_id")
	private String emailId;
	
	@Column(nullable = false, name = "password")
	private String password;

	@ManyToOne
	@JoinColumn(name="privilege_id")
	private Privilege privilege;


	public Users(long userId, String name, long phoneNumber, String emailId, String password, Privilege privilege) {
		super();
		this.userId = userId;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.emailId = emailId;
		this.password = password;
		this.privilege = privilege;
	}

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
				+ ", privilege=" + privilege.getPrivilegeId() + "]";
	}
	
	
}
