package com.psl.idea.controllers;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psl.idea.Constants;
import com.psl.idea.models.Idea;
import com.psl.idea.models.ResponseUser;
import com.psl.idea.models.Theme;
import com.psl.idea.models.User;
import com.psl.idea.models.Users;
import com.psl.idea.service.IdeaService;
import com.psl.idea.service.ThemeService;
import com.psl.idea.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping()
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ThemeService themeService;
	
	@Autowired
	IdeaService ideaService;
	
	@PostMapping("/api/users/register")
	public ResponseEntity<Map<String,Object>> registerUser(@RequestBody Users user) {
		userService.registerUser(user);
		return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
	}

	@PostMapping("/api/users/login")
	public ResponseEntity<Map<String, Object>> loginUser(@RequestBody User user) {	
		Users responseUser = userService.validateUser(user);
		return new ResponseEntity<>(generateJWTToken(responseUser), HttpStatus.OK);
	}
	
	//creates JWT token
	private Map<String,Object> generateJWTToken(Users user){
		long timestamp = System.currentTimeMillis();
		String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_KEY)
				.setIssuedAt(new Date(timestamp))
				.setExpiration(new Date(timestamp + Constants.TOKEN_VALIDAITY))
				.claim("userId", user.getUserId())
				.claim("emailId", user.getEmailId())
				.claim("name",user.getName())
				.claim("privilege", user.getPrivilege())
				.compact();
		Map<String, Object> map = new HashMap<>();
		
		ResponseUser responseuser=new ResponseUser(user.getName(),user.getEmailId(),user.privilege());
		
		map.put("token", token);
		map.put("user", responseuser);
		return map;
	}
	
	@GetMapping("api/loggedin/users/{userId}/themes")
	public List<Theme> getUserThemes(@PathVariable long userId)
	{
		return themeService.getThemesByUser(userId);
	}
	
	@GetMapping("api/loggedin/users/{userId}/ideas")
	public List<Idea> getUserIdeas(@PathVariable long userId)
	{
		return ideaService.getIdeasByUser(userId);
	}
}
