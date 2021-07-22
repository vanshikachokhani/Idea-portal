package com.psl.idea.service;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psl.idea.models.Idea;
import com.psl.idea.models.Theme;
import com.psl.idea.repository.IdeaRepo;
import com.psl.idea.repository.ThemeRepo;

@Service
public class IdeaService{
		
	@Autowired
	ThemeRepo themeRepo;

	@Autowired
	IdeaRepo ideaRepo;

	public List<Theme> viewThemes(){
		return 	themeRepo.findAll();
	}

	public List<Idea> viewIdeas(){
		return ideaRepo.findAll();
	}


	// sort by most likes
	public List<Idea> viewIdeasbyLikes(){
		return ideaRepo.findAll();
	}

	// sort by newest first
	public List<Idea> viewIdeasbyDate(){
		return ideaRepo.findAll();
	}

	//sort by most commented first
	public List<Idea> viewIdeasbyComment(){
		return ideaRepo.findAll();
	}

	public Idea createIdea(long themeId, Idea idea) {
		Theme t = themeRepo.findById(themeId).orElse(null);
		if(t != null)
		{
			idea.setTheme(t);
			return ideaRepo.save(idea);
		}
		else
			System.out.println("Invalid Theme");
		return null;
	}

	public List<Idea> getIdeasByUser(long userId) {
		List<Idea> ideas = new ArrayList<>();
		
		ideas = ideaRepo.findByUserUserId(userId);
		
		return ideas;
	}

}
