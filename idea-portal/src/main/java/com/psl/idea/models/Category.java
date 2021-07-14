package com.psl.idea.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="category")
public class Category {
	
	@Id
	private long categoryId;
	
	private String category;

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
