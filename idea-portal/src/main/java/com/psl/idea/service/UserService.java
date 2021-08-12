package com.psl.idea.service;

import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.psl.idea.exceptions.AuthException;
import com.psl.idea.exceptions.NotFoundException;
import com.psl.idea.models.ConfirmationToken;
import com.psl.idea.models.UpdateUser;
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
	
	public Users validateUser(User user) throws NotFoundException {
		String emailId = user.getEmailId();
		if(emailId!=null) {
			emailId = emailId.toLowerCase();
			Users dbuser = userRepo.findByEmailId(emailId);
			if(dbuser==null)
				throw new AuthException("This email id does not exists");
			if(!BCrypt.checkpw(user.getPassword(), dbuser.getPassword()))
				throw new AuthException("Invalid Login credentials");
			return dbuser;
		}
		else {
			throw new NotFoundException("Email Not Found");
		}
	}
	
	public Users registerUser(Users user) {
		String emailId = user.getEmailId().toLowerCase();
		Pattern pattern = Pattern.compile("^(.+)@(.+)$");
		
		if(!pattern.matcher(emailId).matches())
			throw new AuthException("Invalid email format");
		Users u = userRepo.findByEmailId(emailId);
		
		if(u!=null)
			throw new AuthException("Email is already in use");
		String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
		user.setEmailId(emailId);
		user.setPassword(hashedPassword);
		return userRepo.save(user);
	}
	
	public Users updatePassword(UpdateUser user) throws NotFoundException {
		String emailId = user.getEmailId();
		String password = user.getOldpassword();
		if(emailId!=null) {
			emailId = emailId.toLowerCase();
			Users dbuser = userRepo.findByEmailId(emailId);
			if(dbuser==null)
				throw new AuthException("This email id does not exists.");
			if(!BCrypt.checkpw(password, dbuser.getPassword()))
				throw new AuthException("Incorrect password");	
			String newPassword = user.getNewpassword();
			String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(10));
			dbuser.setPassword(hashedPassword);
			return userRepo.save(dbuser);
		}
		else {
			throw new NotFoundException("Email not found");
		}
	}
	
	public Users updateEmailIdAndCompany(UpdateUserEmail user) throws NotFoundException {
		String emailId = user.getOldemailId();
		String password = user.getPassword();
		if(emailId!=null) {
			emailId = emailId.toLowerCase();
			Users dbuser = userRepo.findByEmailId(emailId);
			if(dbuser==null)
				throw new AuthException("This email id does not exists.");
			if(!BCrypt.checkpw(password, dbuser.getPassword()))
				throw new AuthException("Incorrect password");
			
			String newemailId = user.getNewemailId();
			if(newemailId!=null) {
				dbuser.setEmailId(newemailId.toLowerCase());
			}
			String newcompany = user.getNewcompany();
			if(newcompany!=null) {
				dbuser.setCompany(newcompany);
			}
			dbuser = userRepo.save(dbuser);
			return dbuser;
		}
		else
			throw new NotFoundException("Email not found!");
	}
	
	public Users getUserByUserId(long userId) {
		return userRepo.findByuserId(userId);
	}

	public String forgotPassword(String emailId) {
		emailId = emailId.toLowerCase();
		Users dbuser = userRepo.findByEmailId(emailId);
		ConfirmationToken confirmationToken = new ConfirmationToken(dbuser);
		confirmationToken = confirmationTokenRepo.save(confirmationToken);
		
		SimpleMailMessage mailmsg = new SimpleMailMessage();
		mailmsg.setTo(emailId);
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
		if(!checkToken(token))
			throw new AuthException("Incorrect token");	
		Users dbuser = tokenentity.getUserEntity();
		dbuser = changepassword(dbuser, user.getPassword());
		return dbuser;
	}
	
	public Users changepassword(Users user, String newpassword) {
		String hashedPassword = BCrypt.hashpw(newpassword, BCrypt.gensalt(10));
		user.setPassword(hashedPassword);
		user = userRepo.save(user);
		return user;
	}
}
