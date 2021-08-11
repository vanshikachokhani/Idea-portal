package com.psl.idea.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="idea_files")
public class IdeaFiles extends Files {

	@ManyToOne
	@JoinColumn(name="ideaId", nullable=false)
	private Idea idea;
	
	public IdeaFiles() {
		super();
	}

	public IdeaFiles(String fileName, String fileType, Idea idea) {
		super(fileName, fileType);
		this.idea = idea;
	}
	
	public Idea getIdea() {
		return idea;
	}
	public void setIdea(Idea idea) {
		this.idea = idea;
	}

	@Override
	public String toString() {
		return "Idea File [File Name: " + fileName + ", MimeType: " + fileType + "]";
	}
	
}
