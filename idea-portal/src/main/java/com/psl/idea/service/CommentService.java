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
	
	
	public void createComment(Comment comment,long ideaId) {	
		java.util.Optional<Idea> idea=ideaRepo.findById(ideaId);
		Idea i=ideaRepo.findByIdeaId(ideaId);
		if(idea.isPresent()) {
			comment.setIdea(i);
			repo.save(comment);
		}
		else {
			System.out.println("Invalid comment");
		}
		
	}
	
	public List<Comment> viewComments(Long ideaId){
		java.util.Optional<Idea> idea=ideaRepo.findById(ideaId);
		if(idea.isPresent()) {
			//return idea.get().getComments();
			return repo.findByIdeaIdeaId(ideaId);
		}
		else {
			return new ArrayList<Comment>();
		}
		
	}

}
