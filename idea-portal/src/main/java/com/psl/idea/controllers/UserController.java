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
import com.psl.idea.models.Theme;
import com.psl.idea.models.Users;
import com.psl.idea.service.IdeaService;
import com.psl.idea.service.ThemeService;
import com.psl.idea.service.UserService;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

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
	public ResponseEntity<Map<String,String>> registerUser(@RequestBody Users user) {
		userService.registerUser(user);
		return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap) {
		String email = (String) userMap.get("emailId");
		String password = (String) userMap.get("password");
		
		Users user = userService.validateUser(email, password);
		return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
	}
	
	private Map<String,String> generateJWTToken(Users user){
		long timestamp = System.currentTimeMillis();
		String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_KEY)
				.setIssuedAt(new Date(timestamp))
				.setExpiration(new Date(timestamp + Constants.TOKEN_VALIDAITY))
				.claim("userId", user.getUserId())
				.claim("emailId", user.getEmailId())
				.claim("name",user.getName())
				.claim("privilege", user.getPrivilege())
				.compact();
		Map<String, String> map = new HashMap<>();
		
		map.put("token", token);
		return map;
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
