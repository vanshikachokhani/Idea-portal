package com.psl.idea.models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Files {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long fileId;
	
	@Column(name="filename", nullable=false)
	protected String fileName;
	
	@Column(name="filetype", nullable=false)
	protected String fileType;

	public Files() {
		super();
	}

	public Files(String fileName, String fileType) {
		super();
		this.fileName = fileName;
		this.fileType = fileType;
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
	
}
