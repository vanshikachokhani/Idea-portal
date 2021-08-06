package com.psl.idea.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psl.idea.models.Comment;
import com.psl.idea.models.Idea;
import com.psl.idea.repository.CommentRepo;
import com.psl.idea.repository.IdeaRepo;

@Service
public class CommentService {
	
	@Autowired
	CommentRepo repo;
	
	@Autowired
	IdeaRepo ideaRepo;
	
	
	public Comment createComment(Comment comment,long ideaId) {	
		Idea idea=ideaRepo.findById(ideaId).orElse(null);
		if(idea != null) {
			comment.setIdea(idea);
			return repo.save(comment);
		}
		else {
			return null;
		}
		
	}
	
	public List<Comment> viewComments(Long ideaId){
		java.util.Optional<Idea> idea=ideaRepo.findById(ideaId);
		if(idea.isPresent()) {
			//return idea.get().getComments();
			return repo.findByIdeaIdeaId(ideaId);
		}
		else {
			return new ArrayList<>();
		}
		
	}

}
