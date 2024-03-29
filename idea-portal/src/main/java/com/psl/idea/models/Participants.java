package com.psl.idea.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="participants")
@IdClass(ParticipantsId.class)
public class Participants implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;
	
	@Id
	@ManyToOne
	@JoinColumn(name="idea_id")
	private Idea idea;
	
	@Id
	@ManyToOne
	@JoinColumn(name="role_id")
	private Roles role;
	
	
	public Participants() {
		super();
	}

	public Participants(Users user, Idea idea, Roles role) {
		super();
		this.user = user;
		this.idea = idea;
		this.role = role;
	}

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

