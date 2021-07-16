package com.psl.idea.service;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psl.idea.exceptions.AuthException;
import com.psl.idea.models.Users;
import com.psl.idea.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	UserRepo userRepo;
	
	public Users validateUser(String email_id, String password) throws AuthException {
		if(email_id!=null)
			email_id.toLowerCase();
			Users user =  userRepo.findByemailIdAndPassword(email_id, password);
			if(user==null)
				throw new AuthException("Ivalid Login credentials");
			return user;
	}
	
	public void registerUser(Users user) throws AuthException {
		System.out.println(user.getPassword());
		String email_id = user.getEmailId();
		Pattern pattern = Pattern.compile("^(.+)@(.+)$");
		if(email_id!=null) {
			email_id.toLowerCase();
			user.setEmailId(email_id);
		}
		if(!pattern.matcher(email_id).matches())
			throw new AuthException("Invalid email format");
		Users u = userRepo.findByEmailId(email_id);
		
		if(u!=null)
			throw new AuthException("Email is already in use");
		
		userRepo.save(user);
		
	}
	
}
