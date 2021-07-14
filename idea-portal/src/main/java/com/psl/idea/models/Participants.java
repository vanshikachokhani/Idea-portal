package com.psl.idea.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="participants")
public class Participants {
	
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private Users user;
	
	private Idea idea;
	
	@OneToOne
	@JoinColumn(name="ROLE_ID")
	private Roles role;

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
	
	public Idea getIdea() {
		return idea;
	}

	public void setIdea(Idea idea) {
		this.idea = idea;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Participants [user=" + user + ", idea=" + idea + ", role=" + role + "]";
	}
}

