package com.psl.idea.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="theme_files")
public class ThemeFiles extends Files {
	@ManyToOne
	@JoinColumn(name="themeId")
	private Theme theme;
	
	public ThemeFiles() {
		super();
	}

	public ThemeFiles(String fileName, String fileType, Theme theme) {
		super(fileName, fileType);
		this.theme = theme;
	}
	
	public Theme getTheme() {
		return theme;
	}
	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Theme File [File Name: " + fileName + ", MimeType: " + fileType + "]";
	}
	
}
