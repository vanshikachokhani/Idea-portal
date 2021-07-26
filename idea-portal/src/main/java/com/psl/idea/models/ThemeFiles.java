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
@Table(name="theme_files")
public class ThemeFiles {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long fileId;
	
	@Column(name="filename", nullable=false)
	private String fileName;
	@Column(name="filetype", nullable=false)
	private String fileType;
	
	@ManyToOne
	@JoinColumn(name="themeId")
	private Theme theme;
	
	public ThemeFiles() {
		super();
	}

	public ThemeFiles(String fileName, String fileType, Theme theme) {
		super();
		this.fileName = fileName;
		this.fileType = fileType;
		this.theme = theme;
	}
	
	public long getFileId() {
		return fileId;
	}
	public void setFileId(long fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
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
