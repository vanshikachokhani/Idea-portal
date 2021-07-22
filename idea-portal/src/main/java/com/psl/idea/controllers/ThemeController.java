package com.psl.idea.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.psl.idea.models.Idea;
import com.psl.idea.models.Theme;
import com.psl.idea.service.IdeaService;
import com.psl.idea.service.ThemeService;
import com.psl.idea.util.UsersUtil;


@RestController
@RequestMapping()
public class ThemeController {
	@Autowired
	private IdeaService ideaService;
	@Autowired
	private ThemeService themeService;
	@Autowired
	private UsersUtil usersUtil;
	
     // view all themes
	@GetMapping(path="/api/themes")
    public List<Theme> viewThemes(){
		return themeService.viewThemes();
	}
	
	
	// view all ideas of a particular theme
	@GetMapping(path="/api/loggedin/themes/{themeID}/ideas")
    public List<Idea> viewIdeas(){
		return ideaService.viewIdeas();
	}
	
	
	// sort by most likes
	@GetMapping(path="/api/loggedin/themes/{themeID}/likes")
    public List<Idea> viewIdeasbyLikes(){
		return ideaService.viewIdeasbyLikes();
	}
	
	// sort by newest first
	@GetMapping(path="/api/loggedin/themes/{themeID}/date")
    public List<Idea> viewIdeasbyDate(){
		return ideaService.viewIdeasbyDate();
	}
	
	//sort by most commented first
	@GetMapping(path="/api/loggedin/themes/{themeID}/comment")
    public List<Idea> viewIdeasbyComment(){
		return ideaService.viewIdeasbyComment();
	}
	
	// create a theme
	@PostMapping(path="/api/loggedin/themes")
	public ResponseEntity<Object> createTheme(@RequestBody Theme theme, HttpServletRequest httpServletRequest)
	{
		long userPrivilege = usersUtil.getPrivilegeIdFromRequest(httpServletRequest);
		if(userPrivilege == 1)
		{
			Theme t = themeService.createTheme(theme);
			return ResponseEntity.status(HttpStatus.OK).body(t);
		}
		else
		{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("401: Not Authorized");
		}
	}
	
	// create an idea
	@PostMapping(path="/api/loggedin/themes/{themeId}/ideas")
	public ResponseEntity<Object> createIdea(@PathVariable long themeId, @RequestBody Idea idea, HttpServletRequest httpServletRequest)
	{
		long userPrivilege = usersUtil.getPrivilegeIdFromRequest(httpServletRequest);
		if(userPrivilege == 2)
		{
			Idea i = ideaService.createIdea(themeId, idea);
			if(i != null)
				return ResponseEntity.status(HttpStatus.OK).body(i);
			else
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("404: Theme Not Found! Could not submit Idea");
		}
		else
		{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("401: Unauthorized");
		}
	}

}
