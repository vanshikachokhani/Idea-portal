package com.psl.idea.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ratings")
public class Rating implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	@JoinColumn(name="user_id")
	private Users user;
	
	@Id
	@ManyToOne
	@JoinColumn(name="idea_id")
	private Idea idea;
	
	private boolean rating; // true means liked, false means disliked

	public Rating(Users user, Idea idea, boolean rating) {
		super();
		this.user = user;
		this.idea = idea;
		this.rating = rating;
	}

	public Rating() {
		super();
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

	public boolean isRating() {
		return rating;
	}

	public void setRating(boolean rating) {
		this.rating = rating;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Rating [user=" + user.getUserId() + ", idea=" + idea.getIdeaId() + ", rating=" + rating + "]";
	}
	
}
