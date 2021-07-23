package com.psl.idea.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="category")
public class Category {
	
	@Id
	private long categoryId;
	
	@Column(name="category", nullable=false, unique=true)
	private String category;

	public Category() {
		super();
	}

	public Category(long categoryId, String category) {
		super();
		this.categoryId = categoryId;
		this.category = category;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Category [id=" + categoryId + ", category=" + category + "]";
	}

}
