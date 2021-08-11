package com.psl.idea.service;

import java.util.ArrayList;
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
	IdeaRepo ideaRepo;

	//interested participants in this idea
	public Participants interestIn(Participants participants,Long ideaId) {
		Idea i=ideaRepo.findByIdeaId(ideaId);
		if(i != null) {
			participants.setIdea(i);
			return repo.save(participants);
		}
		else {
			return null;
		}
		
	}
	
	//view interested participants
	public List<Participants> viewInterested(Long ideaId){
		java.util.Optional<Idea> idea=ideaRepo.findById(ideaId);
		if(idea.isPresent()) {
			return repo.findByIdeaIdeaId(ideaId);
		}
		else {
			return new ArrayList<>();
		}
	}
	
	public Idea viewIdea(Long ideaId){
		return ideaRepo.findById(ideaId).orElse(null);
	}

}
