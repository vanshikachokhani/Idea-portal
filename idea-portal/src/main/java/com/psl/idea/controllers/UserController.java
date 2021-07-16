package com.psl.idea.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psl.idea.models.Idea;
import com.psl.idea.models.Theme;
import com.psl.idea.models.Users;
import com.psl.idea.service.IdeaService;
import com.psl.idea.service.ThemeService;
import com.psl.idea.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ThemeService themeService;
	
	@Autowired
	IdeaService ideaService;
	
	@PostMapping("/register")
	public String registerUser(@RequestBody Users user) {
		userService.registerUser(user);
		return "User added";
	}

	@PostMapping("/login")
	public String loginUser(@RequestBody Map<String, Object> userMap) {
		String email = (String) userMap.get("email_id");
		String password = (String) userMap.get("password");
		
		userService.validateUser(email, password);
		return "Logged In";
	}
	
	@GetMapping("/{userId}/themes")
	public List<Theme> getUserThemes(@PathVariable long userId)
	{
		return themeService.getThemesByUser(userId);
	}
	
	@GetMapping("/{userId}/ideas")
	public List<Idea> getUserIdeas(@PathVariable long userId)
	{
		return ideaService.getIdeasByUser(userId);
	}
}
