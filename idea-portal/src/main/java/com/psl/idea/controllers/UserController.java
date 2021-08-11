package com.psl.idea.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.psl.idea.exceptions.AuthException;
import com.psl.idea.exceptions.NotFoundException;
import com.psl.idea.exceptions.UnauthorizedException;
import com.psl.idea.models.Idea;
import com.psl.idea.models.Theme;
import com.psl.idea.models.UpdateUser;
import com.psl.idea.models.UpdateUserEmail;
import com.psl.idea.models.User;
import com.psl.idea.models.Users;
import com.psl.idea.service.IdeaService;
import com.psl.idea.service.ThemeService;
import com.psl.idea.service.UserService;
import com.psl.idea.util.UsersUtil;


@RestController
@RequestMapping
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ThemeService themeService;
	
	@Autowired
	IdeaService ideaService;
	
	@Autowired
	UsersUtil usersUtil;
	
	//Sign-Up 
	@PostMapping("/api/users/register")
	public ResponseEntity<Map<String,Object>> registerUser(@RequestBody Users user) {
		user = userService.registerUser(user);
		return new ResponseEntity<>(usersUtil.generateJWTToken(user), HttpStatus.OK);
	}

	//Login 
	@PostMapping("/api/users/login")
	public ResponseEntity<Map<String, Object>> loginUser(@RequestBody User user) throws NotFoundException {	
		Users responseUser = userService.validateUser(user);
		return new ResponseEntity<>(usersUtil.generateJWTToken(responseUser), HttpStatus.OK);
	}
	
	//Update EmailId && Company 
	@PostMapping("/api/users/update-emailId-company")
	public ResponseEntity<Map<String,Object>> updateEmailIdAndCompany(@RequestBody UpdateUserEmail user) throws NotFoundException {
		Users responseuser = userService.updateEmailIdAndCompany(user);
		if(responseuser != null)
			return new ResponseEntity<>(usersUtil.generateJWTToken(responseuser), HttpStatus.OK);
		else
			throw new NotFoundException("Email Not Found!");
	}
	
	//Update password 
	@PostMapping("/api/users/update-password")
	public ResponseEntity<Map<String,Object>> updatePassword(@RequestBody UpdateUser user) throws NotFoundException {
		Users responseuser = userService.updatePassword(user);
		return new ResponseEntity<>(usersUtil.generateJWTToken(responseuser), HttpStatus.OK);
	}
	
	//Forgot password
	@PostMapping("/api/users/forgot-password")
	public String forgotPassword(@RequestBody Map<String, Object> userMap) {
		String emailId = (String) userMap.get("emailId");
		return userService.forgotPassword(emailId);
	}
	
	@GetMapping("/confirm-account")
	public ResponseEntity<Object> confirmAccount(@RequestParam String token) {
		if(!userService.checkToken(token))
			throw new AuthException("Link expired");
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/confirm-account")
	public ResponseEntity<Map<String,Object>> setPassword(@RequestParam String token, @RequestBody User user) {
		Users responseuser = userService.updateForgotPassword(token, user);
		return new ResponseEntity<>(usersUtil.generateJWTToken(responseuser), HttpStatus.OK);
	}
	
	@GetMapping("/api/loggedin/users/{userId}/themes")
	public ResponseEntity<List<Theme>> getUserThemes(@PathVariable long userId, HttpServletRequest httpServletRequest) {
		if(usersUtil.getPrivilegeIdFromRequest(httpServletRequest) == 1 && userId == (int) httpServletRequest.getAttribute("userId"))
			return new ResponseEntity<>(themeService.getThemesByUser(userId), HttpStatus.OK);
		else
			throw new UnauthorizedException("401: Unauthorized! Incorrect Privilege level!");
	}
	
	@GetMapping("/api/loggedin/users/{userId}/ideas")
	public ResponseEntity<List<Idea>> getUserIdeas(@PathVariable long userId, HttpServletRequest httpServletRequest) {
		if(usersUtil.getPrivilegeIdFromRequest(httpServletRequest) == 2 && userId == (int) httpServletRequest.getAttribute("userId"))
			return new ResponseEntity<>(ideaService.getIdeasByUser(userId), HttpStatus.OK);
		else
			throw new UnauthorizedException("401: Unauthorized! Incorrect Privilege level!");
	}
}
