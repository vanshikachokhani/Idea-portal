package com.psl.idea.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="idea")
public class Idea {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long ideaId;
	
	private String title;
	private String description;
	private String[] files;
	private long rating;
	
	@ManyToOne
	@JoinColumn(name="themeid")
	private Theme theme;
	
	@ManyToOne
	@JoinColumn(name="userid")
	private Users user;
	
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
	public String[] getFiles() {
		return files;
	}
	public void setFiles(String[] files) {
		this.files = files;
	}
	public long getRating() {
		return rating;
	}
	public void setRating(long rating) {
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
		// TODO Auto-generated method stub
		return "Idea [id=" + ideaId + ", title=" + title + ", theme=" + theme.getThemeId() + ", submitted by=" + user.getUserId() + "]";
	}
	
}
