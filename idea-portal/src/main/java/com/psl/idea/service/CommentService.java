package com.psl.idea.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psl.idea.models.Comment;
import com.psl.idea.models.Idea;
import com.psl.idea.models.Users;
import com.psl.idea.repository.CommentRepo;
import com.psl.idea.repository.IdeaRepo;

@Service
public class CommentService {
	
	@Autowired
	CommentRepo repo;
	
	@Autowired
	IdeaRepo ideaRepo;
	
	
	public void createComment(Comment comment,Idea idea, Users user) {	
		comment.setUser(user);
		comment.setIdea(idea);
		repo.save(comment);
		
	}
	
	public List<Comment> viewComments(Long ideaId){
		java.util.Optional<Idea> idea=ideaRepo.findById(ideaId);
		if(idea.isPresent()) {
			return idea.get().getComments();
		}
		else {
			return new ArrayList<Comment>();
		}
		
	}

}
