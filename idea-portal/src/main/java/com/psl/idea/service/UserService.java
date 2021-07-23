package com.psl.idea.service;

import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;
//import org.mindrot.jbcrypt.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.psl.idea.exceptions.AuthException;
import com.psl.idea.models.ConfirmationToken;
import com.psl.idea.models.UpdateUser;
import com.psl.idea.models.UpdateUserCompany;
import com.psl.idea.models.UpdateUserEmail;
import com.psl.idea.models.User;
import com.psl.idea.models.Users;
import com.psl.idea.repository.ConfirmationTokenRepo;
import com.psl.idea.repository.UserRepo;

@Service

public class UserService {
	
	@Autowired
	UserRepo userRepo;

	@Autowired
	JavaMailSender mail;
	
	@Autowired
	ConfirmationTokenRepo confirmationTokenRepo;
	
	public Users validateUser(User user) throws AuthException {
		String email_id = user.getEmailId();
	
		if(email_id!=null)
			email_id.toLowerCase();

			Users dbuser = userRepo.findByEmailId(email_id);

			if(dbuser==null)
				throw new AuthException("This email id does not exists");
			
			if(!BCrypt.checkpw(user.getPassword(), dbuser.getPassword()))
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
	
	public Users updatePassword(UpdateUser user) throws AuthException {
		String email_id = user.getEmailId();
		String password = user.getOldpassword();
		if(email_id!=null)
			email_id.toLowerCase();

			Users dbuser = userRepo.findByEmailId(email_id);
			if(dbuser==null)
				throw new AuthException("This email id does not exists.");
			
			if(!BCrypt.checkpw(password, dbuser.getPassword()))
				throw new AuthException("Incorrect password");
		
		String newPassword = user.getNewpassword();
		String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(10));
		dbuser.setPassword(hashedPassword);
		userRepo.save(dbuser);
		return dbuser;
	}
	
	public Users updateEmailId(UpdateUserEmail user) throws AuthException {
		String email_id = user.getOldemailId();
		String password = user.getPassword();
		if(email_id!=null)
			email_id.toLowerCase();
		Users dbuser = userRepo.findByEmailId(email_id);
		if(dbuser==null)
			throw new AuthException("This email id does not exists.");
		if(!BCrypt.checkpw(password, dbuser.getPassword()))
			throw new AuthException("Incorrect password");
		
		String newemailId = user.getNewemailId();
		dbuser.setEmailId(newemailId);
		userRepo.save(dbuser);
		return dbuser;
	}

	public Users updateCompany(UpdateUserCompany user) throws AuthException {
		String email_id = user.getEmailId();
		String password = user.getPassword();
		if(email_id!=null)
			email_id.toLowerCase();
		Users dbuser = userRepo.findByEmailId(email_id);
		if(dbuser==null)
			throw new AuthException("This email id does not exists.");
		if(!BCrypt.checkpw(password, dbuser.getPassword()))
			throw new AuthException("Incorrect password");
		
		String oldcompany = user.getOldcompany();
		String newcompany = user.getNewcompany();
		
		Users dbusercheck = userRepo.findByemailIdAndCompany(email_id, oldcompany);
		
		if(dbuser!=dbusercheck)
			throw new AuthException("Company and email id do not match");
		
		dbuser.setCompany(newcompany);
		userRepo.save(dbuser);
		return dbuser;
	}
	
	public Users getUserByUserId(long userId) {
		return userRepo.findByuserId(userId);
	}

	public String forgotPassword(String email_id) {
		if(email_id!=null)
			email_id.toLowerCase();
		Users dbuser = userRepo.findByEmailId(email_id);
		
		ConfirmationToken confirmationToken = new ConfirmationToken(dbuser);
//		confirmationToken.validateToken();
		confirmationTokenRepo.save(confirmationToken);
		
		SimpleMailMessage mailmsg = new SimpleMailMessage();
		mailmsg.setTo(email_id);
		mailmsg.setSubject("Password Reset!");
		mailmsg.setText("To reset your password, please enter this : "
	            +"http://localhost:8080/confirm-account?token="
				+confirmationToken.getConfirmationToken());
		mail.send(mailmsg);
		return "Confirmation token sent to mail id";
	
	}
	
	public boolean checkToken(String token) {
		ConfirmationToken tokenentity = confirmationTokenRepo.findByConfirmationToken(token);
		return tokenentity.validateToken();
	}
	
	
	public Users updateForgotPassword(String token, User user) {
		ConfirmationToken tokenentity = confirmationTokenRepo.findByConfirmationToken(token);
//		if(tokenentity.getUserEntity().getEmailId()!=user.getEmailId()) {
//			throw new AuthException("Incorrect token");	
//		}
		if(checkToken(token)==false)
			throw new AuthException("Incorrect token");	
		Users dbuser = tokenentity.getUserEntity();
		dbuser = changepassword(dbuser, user.getPassword());
		return dbuser;
	}
	
	public Users changepassword(Users user, String newpassword) {
		String newPassword = user.getPassword();
		String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(10));
		user.setPassword(hashedPassword);
		userRepo.save(user);
		return user;
	}
}
