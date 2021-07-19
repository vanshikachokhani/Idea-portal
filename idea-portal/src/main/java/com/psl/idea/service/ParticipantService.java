package com.psl.idea.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psl.idea.models.Idea;
import com.psl.idea.models.Participants;
import com.psl.idea.repository.IdeaRepo;
import com.psl.idea.repository.ParticipantRepo;


@Service
public class ParticipantService {
	
	@Autowired
	ParticipantRepo repo;
	
	@Autowired
	IdeaRepo repo1;

	public void createIdea(Idea idea) {	
		idea.setTheme(null);
		idea.setUser(null);
		repo1.save(idea);
	}
	
	//interested participants in this idea
	public void interestIn(Participants participants) {
		participants.setIdea(null);
		participants.setRole(null);
		participants.setUser(null);
		repo.save(null);
	}
	
	//view interested participants
	public List<Participants> viewInterested(){
		return repo.findAll();
	}
	
	public Idea viewIdea(Long l){
		return repo1.getById(l);
	}

}
