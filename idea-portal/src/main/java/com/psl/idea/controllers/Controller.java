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
@RequestMapping("/control")
public class Controller {

	@Autowired(required = false)
	IdeaService service;
	@GetMapping(path="/view")
    public List<Theme> viewThemes(){
		// view all themes
		List<Theme> ans = null;
		return ans;
	}
	
	@GetMapping(path="/view/{themeID}")
    public List<Idea> viewIdeas(){
		// view all themes
		List<Idea> ans = null;
		return ans;
	}
}
