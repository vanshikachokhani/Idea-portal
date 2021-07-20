package com.psl.idea.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="ideas")
public class Idea {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idea_id")
	private long ideaId;
	
	@Column(name="title", nullable=false)
	private String title;
	@Column(name="description", nullable=false)
	private String description;
	private String[] files;
	
	@Column(name="rating")
	private float rating = 0;
	
	@ManyToOne
	@JoinColumn(name="theme_id")
	private Theme theme;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private Users user;
	
	/*@OneToMany(mappedBy = "idea",cascade = CascadeType.ALL)
	private List<Comment> comments;
	
	@OneToMany(mappedBy = "idea",cascade =CascadeType.ALL)
	private List<Rating> ratings;
	
	@OneToMany(mappedBy = "idea", cascade = CascadeType.ALL)
	private List<Participants> participant;
	
	
	
	
	public List<Participants> getParticipant() {
		return participant;
	}
	public void setParticipant(List<Participants> participant) {
		this.participant = participant;
	}
	public List<Rating> getRatings() {
		return ratings;
	}
	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	*/
	public Idea() {
		super();
	}
	public Idea(String title, String description, String[] files, float rating, Theme theme, Users user) {
		super();
		this.title = title;
		this.description = description;
		this.files = files;
		this.rating = rating;
		this.theme = theme;
		this.user = user;
	}
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
	
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
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
