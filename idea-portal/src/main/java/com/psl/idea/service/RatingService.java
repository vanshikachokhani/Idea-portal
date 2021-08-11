package com.psl.idea.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psl.idea.exceptions.NotFoundException;
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
	
	public void doLike(Long ideaId,Rating rate) throws NotFoundException {
		Idea idea=ideaRepo.findById(ideaId).orElse(null);
		if(idea != null) {
			int p=repo.findTruevalue();
			int q=repo.findTotal();
			
			if(q==0)
			{
				idea.setRating(0f);
			}
			else {
			float r=(float)(p/q)*5;
			idea.setRating(r);
			}
			rate.setIdea(idea);
			repo.save(rate);
		}
		else {
			throw new NotFoundException("Invalid Idea");
		}
	}
	
	public List<Rating> viewRating(Long ideaId) {
		java.util.Optional<Idea> idea=ideaRepo.findById(ideaId);
		if(idea.isPresent()) {
			return repo.findByIdeaIdeaId(ideaId);
		}
		else {
			return new ArrayList<>();
		}
		
	}
	
	

}
