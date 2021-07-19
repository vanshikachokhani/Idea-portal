package com.psl.idea.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psl.idea.models.Rating;
import com.psl.idea.repository.RatingRepo;

@Service
public class RatingService {
	
	@Autowired
	RatingRepo repo;
	
	public void doLike(Rating rate) {
		rate.setUser(null);
		rate.setIdea(null);
		repo.save(rate);
		
	}
	
	public List<Rating> viewRating() {
		return repo.findAll();
		
	}
	
	

}
