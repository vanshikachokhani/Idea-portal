package com.psl.idea.service;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.psl.idea.exceptions.AuthException;
import com.psl.idea.exceptions.NotFoundException;
import com.psl.idea.models.ConfirmationToken;
import com.psl.idea.models.Privilege;
import com.psl.idea.models.UpdateUser;
import com.psl.idea.models.UpdateUserEmail;
import com.psl.idea.models.User;
import com.psl.idea.models.Users;
import com.psl.idea.repository.ConfirmationTokenRepo;
import com.psl.idea.repository.UserRepo;

@WebMvcTest(controllers=UserService.class)
@ExtendWith(SpringExtension.class)
public class UserServiceTest {
	
	@MockBean
	UserRepo userRepo;
	@MockBean
	JavaMailSender javaMailSender;
	@MockBean
	ConfirmationTokenRepo confirmationTokenRepo;
	
	@Autowired
	MockMvc mockMvc;
	@Autowired
	UserService userService;
	
	Privilege cpPrivilege = new Privilege(1, "Client Partner");
	Users user = new Users(1, "John Doe", "1234567890", "john@gmail.com", BCrypt.hashpw("johnDoe", BCrypt.gensalt(10)), "Example", cpPrivilege);
	ConfirmationToken token = new ConfirmationToken(user);
	ConfirmationToken fakeToken = new ConfirmationToken();
	
	@Test
	public void validateUserTest() throws NotFoundException {
		User mockUser = new User();
		mockUser.setPassword("johnDoe");
		when(userRepo.findByEmailId(any(String.class))).thenReturn(user, (Users) null, user);
		
		NotFoundException nf = assertThrows(NotFoundException.class, () -> { userService.validateUser(mockUser); } );
		assertEquals("Email Not Found", nf.getMessage());

		mockUser.setEmailId(user.getEmailId());
		
		assertEquals(user, userService.validateUser(mockUser));
		
		AuthException exception = assertThrows(AuthException.class, () -> { userService.validateUser(mockUser); } );
		assertEquals("This email id does not exists", exception.getMessage());
		
		mockUser.setPassword("JohnDoe");
		exception = assertThrows(AuthException.class, () -> { userService.validateUser(mockUser); } );
		assertEquals("Invalid Login credentials", exception.getMessage());
	}
	
	@Test
	public void registerUserTest() {
		when(userRepo.findByEmailId(any(String.class))).thenReturn(user, (Users) null);
		when(userRepo.save(any(Users.class))).thenReturn(user);
		
		user.setEmailId("gmail.com");
		
		AuthException a = assertThrows(AuthException.class, () -> { userService.registerUser(user); } );
		assertEquals("Invalid email format", a.getMessage());
		
		user.setEmailId("john@gmail.com");
		
		a = assertThrows(AuthException.class, () -> {userService.registerUser(user);} );
		assertEquals("Email is already in use", a.getMessage());
		
		assertEquals(user, userService.registerUser(user));
	}
	
	@Test
	public void updatePasswordTest() throws NotFoundException {
		UpdateUser mockUser = new UpdateUser();
		mockUser.setEmailId(null);
		
		NotFoundException nf = assertThrows(NotFoundException.class, () -> { userService.updatePassword(mockUser); } );
		assertEquals("Email not found", nf.getMessage());
		
		when(userRepo.findByEmailId(any(String.class))).thenReturn(null, user);
		when(userRepo.save(any(Users.class))).thenReturn(user);
		
		mockUser.setEmailId(user.getEmailId());
		mockUser.setOldpassword("JohnDoe");
		mockUser.setNewpassword("JohnDoe");
		
		AuthException a = assertThrows(AuthException.class, () -> { userService.updatePassword(mockUser); } );
		assertEquals("This email id does not exists.", a.getMessage());

		a = assertThrows(AuthException.class, () -> { userService.updatePassword(mockUser); } );
		assertEquals("Incorrect password", a.getMessage());
		
		mockUser.setOldpassword("johnDoe");
		
		assertEquals(user, userService.updatePassword(mockUser));
	}
	
	@Test
	public void updateEmailAndCompanyTest() throws NotFoundException {
		UpdateUserEmail mockUser = new UpdateUserEmail();
		
		NotFoundException nf = assertThrows(NotFoundException.class, () -> { userService.updateEmailIdAndCompany(mockUser); });
		assertEquals("Email not found!", nf.getMessage());
		
		when(userRepo.findByEmailId(any(String.class))).thenReturn(null, user);
		when(userRepo.save(any(Users.class))).thenReturn(user);
		
		mockUser.setOldemailId(user.getEmailId());
		mockUser.setOldcompany("ABC");
		mockUser.setPassword("JohnDoe");
		
		AuthException a = assertThrows(AuthException.class, () -> { userService.updateEmailIdAndCompany(mockUser); });
		assertEquals("This email id does not exists.", a.getMessage());
		
		a = assertThrows(AuthException.class, () -> { userService.updateEmailIdAndCompany(mockUser); });
		assertEquals("Incorrect password", a.getMessage());
		
		mockUser.setPassword("johnDoe");

		assertEquals(user, userService.updateEmailIdAndCompany(mockUser));
		mockUser.setNewcompany("Persistent");
		assertEquals(user, userService.updateEmailIdAndCompany(mockUser));
		mockUser.setNewcompany(null);
		mockUser.setNewemailId("johndoe@gmail.com");
		assertEquals(user, userService.updateEmailIdAndCompany(mockUser));
		mockUser.setNewcompany("Persistent");
		assertEquals(user, userService.updateEmailIdAndCompany(mockUser));
	}
	
	@Test
	public void getUserByUserIdTest() {
		when(userRepo.findByuserId(any(Long.class))).thenReturn(user, (Users) null);
		
		assertEquals(user, userService.getUserByUserId(1));
		assertEquals(null, userService.getUserByUserId(2));
	}
	
	@Test
	public void forgotPasswordTest() {
		when(userRepo.findByEmailId(any(String.class))).thenReturn(user);
		when(confirmationTokenRepo.save(any(ConfirmationToken.class))).thenReturn(token);
		
		assertEquals("Confirmation token sent to mail id", userService.forgotPassword("john@example.test"));
	}
	
	@Test
	public void checkTokenTest() {
		when(confirmationTokenRepo.findByConfirmationToken(any(String.class))).thenReturn(token);
		
		assertEquals(true, userService.checkToken(token.getConfirmationToken()));
	}
	
	@Test
	public void updateForgotPasswordTest() {
		User mockUser = new User();
		when(confirmationTokenRepo.findByConfirmationToken(any(String.class))).thenReturn(token);
		when(userRepo.save(any(Users.class))).thenReturn(user);
		
		mockUser.setEmailId("john@gmail.com");
		mockUser.setPassword("JohnDoe");
		
		assertEquals(user, userService.updateForgotPassword(token.getConfirmationToken(), mockUser));
	}
	
}
