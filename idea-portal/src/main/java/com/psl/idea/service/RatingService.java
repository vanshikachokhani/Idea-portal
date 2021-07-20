package com.psl.idea.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psl.idea.models.Idea;
import com.psl.idea.models.Rating;
import com.psl.idea.repository.IdeaRepo;
import com.psl.idea.repository.RatingRepo;

@Service
public class RatingService {
	
	@Autowired
	RatingRepo repo;
	
	@Autowired
	IdeaRepo ideaRepo;
	
	public void doLike(Long ideaId,Rating rate) {
		java.util.Optional<Idea> idea=ideaRepo.findById(ideaId);
		if(idea.isPresent()) {
			repo.save(rate);
		}
		else {
			System.out.println("Invalid rating");
		}
	}
	
	public List<Rating> viewRating(Long ideaId) {
		java.util.Optional<Idea> idea=ideaRepo.findById(ideaId);
		if(idea.isPresent()) {
			//return idea.get().getRatings();
			return repo.findByIdeaIdeaId(ideaId);
		}
		else {
			return new ArrayList<Rating>();
		}
		
	}
	
	

}
