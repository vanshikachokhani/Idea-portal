package com.psl.idea.service;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.psl.idea.models.Privilege;
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
	Users user = new Users(1, "John Doe", "1234567890", "john@gmail.com", "johnDoe", "Example", cpPrivilege);

	@Test
	public void getUserByUserIdTest() {
		when(userRepo.findByuserId(any(Long.class))).thenReturn(user, (Users) null);
		
		assertEquals(user, userService.getUserByUserId(1));
		assertEquals(null, userService.getUserByUserId(2));
	}
	
}
