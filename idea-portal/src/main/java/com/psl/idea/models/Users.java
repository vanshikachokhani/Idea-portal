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
	private String phoneNumber;
	
	@Column(nullable = false, unique = true, name = "email_id")
	private String emailId;
	
	@Column(nullable = false, name = "password")
	private String password;

	@ManyToOne
	@JoinColumn(name="privilege_id")
	private Privilege privilege;
	
	/*@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Comment> comments;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Rating> ratings;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Participants> participant;
	
	
	public List<Participants> getParticipant() {
		return participant;
	}

	public void setParticipant(List<Participants> participant) {
		this.participant = participant;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}*/
	
	public Users() {
		super();
	}

	public Users(String name, String phoneNumber, String emailId, String password, Privilege privilege) {
		super();
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.emailId = emailId;
		this.password = password;
		this.privilege = privilege;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



	@Override
	public String toString() {
		return "Users [userId=" + userId + ", name=" + name + ", phoneNumber=" + phoneNumber + ", emailId=" + emailId
				+ ", privilege=" + privilege.getPrivilegeId() + "]";
	}
	
	
}
