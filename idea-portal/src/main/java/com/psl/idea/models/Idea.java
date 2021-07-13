package com.psl.idea.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="idea")
public class Idea {

	@Id
	private long ideaId;
	
	private String title;
	private String description;
	private String[] files;
	private long themeId;
	private long rating;
	private long userId;
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
	public long getThemeId() {
		return themeId;
	}
	public void setThemeId(long themeId) {
		this.themeId = themeId;
	}
	public long getRating() {
		return rating;
	}
	public void setRating(long rating) {
		this.rating = rating;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Idea [id=" + ideaId + ", title=" + title + ", theme=" + themeId + ", submitted by=" + userId + "]";
	}
	
}
