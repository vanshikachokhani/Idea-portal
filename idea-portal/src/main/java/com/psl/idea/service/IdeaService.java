package com.psl.idea.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.psl.idea.models.Idea;
import com.psl.idea.models.Theme;
import com.psl.idea.repository.IdeaRepo;
import com.psl.idea.repository.IdeaRepoImpl;
import com.psl.idea.repository.ThemeRepo;

@Service
public class IdeaService{
		
	@Autowired
	ThemeRepo themeRepo;

	@Autowired
	IdeaRepo ideaRepo;


	@Autowired
	IdeaRepoImpl ideaRepoimpl;
	public List<Theme> viewThemes(){
		return 	themeRepo.findAll();
	}

	public List<Idea> viewIdeas(long themeID){
		return ideaRepo.findAll();
	}


	// sort by most likes
	public List<Idea> viewIdeasbyLikes(){
		return ideaRepoimpl.findbylike();
	}

	// sort by newest first
	public List<Idea> viewIdeasbyDate(){
		return ideaRepoimpl.findbydate();
	}

	//sort by most commented first
	public List<Idea> viewIdeasbyComment(){
		return ideaRepoimpl.findbycomment();
	}

	public void createIdea(long themeId, Idea idea) {
		Theme t = themeRepo.findById(themeId).orElse(null);
		
		if(t != null)
		{
			idea.setTheme(t);
			ideaRepo.save(idea);
		}
		else
			System.out.println("Invalid Theme");
		
	}

	public List<Idea> getIdeasByUser(long userId) {
		return ideaRepoimpl.findbyUserUserId(userId);
		
	}

}
