package com.psl.idea.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.psl.idea.models.Idea;
import com.psl.idea.models.Theme;
import com.psl.idea.repository.ThemeRepo;
import com.psl.idea.service.IdeaService;
import com.psl.idea.service.ThemeService;


@RestController
@RequestMapping("/themes")
public class ThemeController {
	@Autowired
	IdeaService ideaService;
	
	@Autowired
	ThemeService themeService;
	
     // view all themes
	@GetMapping(path="")
    public List<Theme> viewThemes(){
		return themeService.viewThemes();
	}
	
	
	// view all ideas of a particular theme
	@GetMapping(path="/{themeID}/ideas")
    public List<Idea> viewIdeas(@PathVariable long themeID){
		return ideaService.viewIdeas(themeID);
	}
	
	
	// sort by most likes
	@GetMapping(path="/{themeID}/likes")
    public List<Idea> viewIdeasbyLikes(@PathVariable long themeID){
		return ideaService.viewIdeasbyLikes();
	}
	
	// sort by newest first
	@GetMapping(path="/{themeID}/date")
    public List<Idea> viewIdeasbyDate(@PathVariable long themeID){
		return ideaService.viewIdeasbyDate();
	}
	
	//sort by most commented first
	@GetMapping(path="/{themeID}/comment")
    public List<Idea> viewIdeasbyComment(@PathVariable long themeID){
		return ideaService.viewIdeasbyComment();
	}
	
	// create a theme
	@PostMapping(path="")
	public void createTheme(@RequestBody Theme theme)
	{
		System.out.println("Create theme");
		themeService.createTheme(theme);
	}
	
	// create an idea
	@PostMapping(path="/{themeId}/ideas")
	public void createIdea(@PathVariable long themeId, @RequestBody Idea idea)
	{
		ideaService.createIdea(themeId, idea);
	}

}
