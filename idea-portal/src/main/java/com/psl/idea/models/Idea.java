package com.psl.idea.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="ideas")
public class Idea {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idea_id")
	private long ideaId;
	
	@Column(name="title", nullable=false)
	private String title;
	@Column(name="description", nullable=false)
	private String description;
	
	@Column(name="rating")
	private float rating = 0;
	
	@ManyToOne
	@JoinColumn(name="theme_id", nullable=false)
	private Theme theme;
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private Users user;
	
	public Idea() {
		super();
	}
	
	public Idea(long ideaId, String title, String description, float rating, Theme theme, Users user) {
		super();
		this.ideaId = ideaId;
		this.title = title;
		this.description = description;
		this.rating = rating;
		this.theme = theme;
		this.user = user;
	}

	public Idea(String title, String description, float rating, Theme theme, Users user) {
		super();
		this.title = title;
		this.description = description;
		this.rating = rating;
		this.theme = theme;
		this.user = user;
	}
	public long getIdeaId() {
		return ideaId;
	}
	public void setIdeaId(long ideaId) {
		this.ideaId = ideaId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public Theme getTheme() {
		return theme;
	}
	public void setTheme(Theme theme) {
		this.theme = theme;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Idea [id=" + ideaId + ", title=" + title + ", theme=" + theme.getThemeId() + ", submitted by=" + user.getUserId() + "]";
	}
	
}
