package com.psl.idea.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.psl.idea.models.Idea;
import com.psl.idea.models.Theme;
import com.psl.idea.service.IdeaService;


@RestController
@RequestMapping("/theme")
public class ThemeController {
	@Autowired
	IdeaService service;
	
	
	@GetMapping(path="/")
    public List<Theme> viewThemes(){
		return service.viewThemes();
	}
	
	@GetMapping(path="/{themeID}")
    public List<Idea> viewIdeas(){
		return service.viewIdeas();
	}
	
	
	// sort by most likes
	@GetMapping(path="/{themeID}/likes")
    public List<Idea> viewIdeasbyLikes(){
		return service.viewIdeasbyLikes();
	}
	
	// sort by newest first
	@GetMapping(path="/{themeID}/date")
    public List<Idea> viewIdeasbyDate(){
		return service.viewIdeasbyDate();
	}
	
	//sort by most commented first
	@GetMapping(path="/{themeID}/comment")
    public List<Idea> viewIdeasbyComment(){
		return service.viewIdeasbyComment();
	}
	
}
