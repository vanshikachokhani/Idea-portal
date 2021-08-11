package com.psl.idea.models;

import java.io.Serializable;
import java.util.Objects;

public class ParticipantsId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private long user;
	private long idea;
	private long role;
	public ParticipantsId(long user, long idea, long role) {
		super();
		this.user = user;
		this.idea = idea;
		this.role = role;
	}
	public ParticipantsId() {
		super();
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
		return idea == other.idea && role == other.role && user == other.user;
	}
	
	
	
	

}
