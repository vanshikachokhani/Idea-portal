package com.psl.idea.models;

import java.io.Serializable;
import java.util.Objects;

public class ParticipantsId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Users user;
	private Idea idea;
	private Roles role;
	public ParticipantsId(Users user, Idea idea, Roles role) {
		super();
		this.user = user;
		this.idea = idea;
		this.role = role;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(idea, role, user);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParticipantsId other = (ParticipantsId) obj;
		return Objects.equals(idea, other.idea) && Objects.equals(role, other.role) && Objects.equals(user, other.user);
	}
	
	

}
