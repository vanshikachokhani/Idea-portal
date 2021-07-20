package com.psl.idea.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psl.idea.models.Idea;
import com.psl.idea.models.Rating;
import com.psl.idea.models.Users;
import com.psl.idea.repository.IdeaRepo;
import com.psl.idea.repository.RatingRepo;

@Service
public class RatingService {
	
	@Autowired
	RatingRepo repo;
	
	@Autowired
	IdeaRepo ideaRepo;
	
	public void doLike(Rating rate, Idea idea, Users user) {
		rate.setUser(user);
		rate.setIdea(idea);
		repo.save(rate);
		
	}
	
	public List<Rating> viewRating(Long ideaId) {
		java.util.Optional<Idea> idea=ideaRepo.findById(ideaId);
		if(idea.isPresent()) {
			return idea.get().getRatings();
		}
		else {
			return new ArrayList<Rating>();
		}
		
	}
	
	

}
