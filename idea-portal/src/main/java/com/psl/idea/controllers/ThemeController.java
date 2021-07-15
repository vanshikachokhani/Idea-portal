package com.psl.idea.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.psl.idea.models.Idea;
import com.psl.idea.models.Theme;
import com.psl.idea.service.IdeaService;
import com.psl.idea.service.ThemeService;


@RestController
@RequestMapping("/themes")
public class ThemeController {
	@Autowired
	IdeaService ideaService;
	
	@Autowired
	ThemeService themeService;
	
	
	@GetMapping(path="/")
    public List<Theme> viewThemes(){
		return themeService.viewThemes();
	}
	
	@PostMapping(path="/add")
    public void addThemes(@RequestBody Theme t){
	themeService.createTheme(t);
	}

	
	@GetMapping(path="/{themeID}")
    public List<Idea> viewIdeas(){
		return ideaService.viewIdeas();
	}
	
	
	// sort by most likes
	@GetMapping(path="/{themeID}/likes")
    public List<Idea> viewIdeasbyLikes(){
		return ideaService.viewIdeasbyLikes();
	}
	
	// sort by newest first
	@GetMapping(path="/{themeID}/date")
    public List<Idea> viewIdeasbyDate(){
		return ideaService.viewIdeasbyDate();
	}
	
	//sort by most commented first
	@GetMapping(path="/{themeID}/comment")
    public List<Idea> viewIdeasbyComment(){
		return ideaService.viewIdeasbyComment();
	}
	
	// create a theme
	@PostMapping(path="/")
	public void createTheme(@RequestBody Theme theme)
	{
		System.out.println("Post Request CreateTheme");
		System.out.println(theme);
		themeService.createTheme(theme);
	}

}
