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
@Table(name="idea_files")
public class IdeaFiles {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long fileId;
	
	@Column(name="filename", nullable=false)
	private String fileName;
	@Column(name="filetype", nullable=false)
	private String fileType;
	
	@ManyToOne
	@JoinColumn(name="ideaId", nullable=false)
	private Idea idea;
	
	public IdeaFiles() {
		super();
	}

	public IdeaFiles(String fileName, String fileType, Idea idea) {
		super();
		this.fileName = fileName;
		this.fileType = fileType;
		this.idea = idea;
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
	public Idea getIdea() {
		return idea;
	}
	public void setIdea(Idea idea) {
		this.idea = idea;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Idea File [File Name: " + fileName + ", MimeType: " + fileType + "]";
	}
	
}
