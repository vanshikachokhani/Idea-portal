package com.psl.idea.service;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.psl.idea.models.Idea;
import com.psl.idea.models.Theme;
import com.psl.idea.repository.IdeaRepo;
import com.psl.idea.repository.ThemeRepo;

@Service
public class IdeaService{
	
@Autowired
ThemeRepo repo;

@Autowired
IdeaRepo repo1;
	
public List<Theme> viewThemes(){
	return 	repo.findAll();
}

public List<Idea> viewIdeas(){
	return repo1.findAll();
}


// sort by most likes
public List<Idea> viewIdeasbyLikes(){
	return repo1.findAll();
}

// sort by newest first
public List<Idea> viewIdeasbyDate(){
	return repo1.findAll();
}

//sort by most commented first
public List<Idea> viewIdeasbyComment(){
	return repo1.findAll();
}



}
