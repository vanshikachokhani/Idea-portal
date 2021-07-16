package com.psl.idea.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psl.idea.models.Users;
import com.psl.idea.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
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
}
