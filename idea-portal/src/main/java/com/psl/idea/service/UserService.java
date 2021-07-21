package com.psl.idea.service;

import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;
//import org.mindrot.jbcrypt.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psl.idea.exceptions.AuthException;
import com.psl.idea.models.User;
import com.psl.idea.models.Users;
import com.psl.idea.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	UserRepo userRepo;
	
	public Users validateUser(User user) throws AuthException {
		String email_id = user.getEmailId();
		String password = user.getPassword();
		if(email_id!=null)
			email_id.toLowerCase();

			Users dbuser = userRepo.findByEmailId(email_id);

			if(!BCrypt.checkpw(password, dbuser.getPassword()))
				throw new AuthException("Invalid Login credentials");
			return dbuser;
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

	public Users getUserByUserId(long userId) {
		return userRepo.findByuserId(userId);
	}
	
}