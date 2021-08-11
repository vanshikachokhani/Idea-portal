package com.psl.idea.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psl.idea.exceptions.NotFoundException;
import com.psl.idea.models.Idea;
import com.psl.idea.models.Participants;
import com.psl.idea.repository.IdeaRepo;
import com.psl.idea.repository.ParticipantRepo;


@Service
public class ParticipantService {
	
	@Autowired
	ParticipantRepo repo;
	
	@Autowired
	IdeaRepo ideaRepo;

	//interested participants in this idea
	public void interestIn(Participants participants,Long ideaId) throws NotFoundException {
		java.util.Optional<Idea> idea=ideaRepo.findById(ideaId);
		Idea i=ideaRepo.findByIdeaId(ideaId);
		if(idea.isPresent()) {
			participants.setIdea(i);
			repo.save(participants);
		}
		else {
			throw new NotFoundException("Invalid Participation");
		}
		
	}
	
	//view interested participants
	public List<Participants> viewInterested(Long ideaId){
		java.util.Optional<Idea> idea=ideaRepo.findById(ideaId);
		if(idea.isPresent()) {
			return repo.findByIdeaIdeaId(ideaId);
		}
		else {
			return new ArrayList<Participants>();
		}
	}
	
	public Idea viewIdea(Long ideaId){
		Idea idea=ideaRepo.findById(ideaId).orElse(null);
		return idea;
	}

}
