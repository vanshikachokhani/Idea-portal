package com.psl.idea.models;

import java.io.Serializable;
import java.util.Objects;

public class RatingId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long user;
	
	
	private long idea;
	
	public RatingId() {
		super();
	}


	public RatingId(long user, long idea) {
		super();
		this.user = user;
		this.idea = idea;
	}


	@Override
	public int hashCode() {
		return Objects.hash(idea, user);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RatingId other = (RatingId) obj;
		return idea == other.idea && user == other.user;
	}
	
	
	
	
	
	
	
}
