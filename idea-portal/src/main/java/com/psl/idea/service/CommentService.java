package com.psl.idea.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psl.idea.models.Comment;
import com.psl.idea.repository.CommentRepo;

@Service
public class CommentService {
	
	@Autowired
	CommentRepo repo;
	
	public void createComment(Comment comment) {	
		comment.setUser(null);
		comment.setIdea(null);
		repo.save(comment);
		
	}
	
	public List<Comment> viewComments(){
		return repo.findAll();
	}

}
