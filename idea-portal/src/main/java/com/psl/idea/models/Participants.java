package com.psl.idea.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="participants")
public class Participants implements Serializable{
	
	@Id
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;
	
	@Id
	@ManyToOne
	@JoinColumn(name="idea_id")
	private Idea idea;
	
	@Id
	@OneToOne
	@JoinColumn(name="role_id")
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
		return "Participants [user=" + user.getUserId() + ", idea=" + idea.getIdeaId() + ", role=" + role.getRoleId() + "]";
	}
}

