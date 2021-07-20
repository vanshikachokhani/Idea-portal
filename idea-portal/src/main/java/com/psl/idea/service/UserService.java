package com.psl.idea.service;

import java.util.List;
import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;
//import org.mindrot.jbcrypt.*;
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
//			System.out.println(email_id);
			Users user = userRepo.findByEmailId(email_id);
			
//			System.out.println(user.getPassword());
			if(!BCrypt.checkpw(password, user.getPassword()))
				throw new AuthException("Invalid Login credentials");
//			Users users =  userRepo.findByemailIdAndPassword(email_id, password);
//			if(users==null)
//				throw new AuthException("Invalid Login credentials");
			return user;
	}
	
	public void registerUser(Users user) throws AuthException {
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
		String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
		user.setPassword(hashedPassword);
		userRepo.save(user);
		
	}
	
}
