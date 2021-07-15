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
@Table(name="themes")
public class Theme {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long themeId;
	@Column(name="title", nullable=false)
	private String title;
	@Column(name="description", nullable=false)
	private String description;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private Users user;
	
	private String[] files;
	
	public Theme()
	{
		super();
	}

	public Theme(String title, String description, Category category, String[] files) {
		super();
		this.title = title;
		this.description = description;
		this.files = files;
		this.category = category;
	}

	public long getThemeId() {
		return themeId;
	}

	public void setThemeId(long themeId) {
		this.themeId = themeId;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String[] getFiles() {
		return files;
	}

	public void setFiles(String[] files) {
		this.files = files;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Theme [id=" + themeId + ", title=" + title + ", category=" + category.getCategoryId() + ", user=" + user.getUserId() + "]";
	}
	
}
