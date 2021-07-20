package com.psl.idea.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psl.idea.models.Idea;
import com.psl.idea.models.Participants;
import com.psl.idea.models.Roles;
import com.psl.idea.models.Users;
import com.psl.idea.repository.IdeaRepo;
import com.psl.idea.repository.ParticipantRepo;


@Service
public class ParticipantService {
	
	@Autowired
	ParticipantRepo repo;
	
	@Autowired
	IdeaRepo ideaRepo;

	//interested participants in this idea
	public void interestIn(Participants participants,Idea idea, Users user, Roles role) {
		participants.setIdea(idea);
		participants.setRole(role);
		participants.setUser(user);
		repo.save(participants);
	}
	
	//view interested participants
	public List<Participants> viewInterested(Long ideaId){
		java.util.Optional<Idea> idea=ideaRepo.findById(ideaId);
		if(idea.isPresent()) {
			return idea.get().getParticipant();
		}
		else {
			return new ArrayList<Participants>();
		}
	}
	
	public Idea viewIdea(Long ideaId){
		java.util.Optional<Idea> idea=ideaRepo.findById(ideaId);
		if(idea.isPresent()) {
		   return ideaRepo.getById(ideaId);
		}
		else
		{
			return new Idea();
		}
	}

}
