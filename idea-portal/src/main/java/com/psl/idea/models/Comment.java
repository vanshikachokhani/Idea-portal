package com.psl.idea.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="comment")
public class Comment {
	
	@Id
	private long commentId;
	
	private String comment;
	private long userId;
	private long ideaId;
	public long getCommentId() {
		return commentId;
	}
	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getIdeaId() {
		return ideaId;
	}
	public void setIdeaId(long ideaId) {
		this.ideaId = ideaId;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Comment [id=" + commentId + ", user=" + userId + ", idea=" + ideaId + "]";
	}

}
