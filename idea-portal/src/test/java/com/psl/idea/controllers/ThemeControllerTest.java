package com.psl.idea.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import static org.mockito.ArgumentMatchers.any;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.psl.idea.Constants;
import com.psl.idea.models.Category;
import com.psl.idea.models.Idea;
import com.psl.idea.models.Privilege;
import com.psl.idea.models.Theme;
import com.psl.idea.models.Users;
import com.psl.idea.service.IdeaService;
import com.psl.idea.service.ThemeService;
import com.psl.idea.util.UsersUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@WebMvcTest(controllers = ThemeController.class)
@ExtendWith(SpringExtension.class)
public class ThemeControllerTest {

	@MockBean
	IdeaService ideaService;
	@MockBean
	ThemeService themeService;
	@MockBean
	UsersUtil usersUtil;
	
//	@MockBean
//	private ThemeRepo themeRepo;
//	@MockBean
//	private UserRepo userRepo;
//	@MockBean
//	private CommentRepo commentRepo;
//	@MockBean
//	private IdeaRepo ideaRepo;
//	@MockBean
//	private ParticipantRepo participantRepo;
//	@MockBean
//	private RatingRepo ratingRepo;
	@MockBean
	private SessionFactory sessionFactory;

	private Privilege cpPrivilege = new Privilege(1, "Client Partner");
	private Privilege pmPrivilege = new Privilege(2, "Product Manager");
	private Category webapp = new Category(1, "WebApp");
	private Users user1 = new Users(1, "Rohan Rathi", "8830850720", "rathirohan8@gmail.com", "$2a$10$TdFsgRMeUDN3IEuKuryH4etBNpN7hY.iWMi83gzjskNMSLUQw7jJe", cpPrivilege);
	private Users user2 = new Users(2, "Rohan Rathi", "8830850720", "rathirohan8@gmail.com", "$2a$10$TdFsgRMeUDN3IEuKuryH4etBNpN7hY.iWMi83gzjskNMSLUQw7jJe", pmPrivilege);
	private String[] files = {};
	private Theme t = new Theme(1, "Test Theme", "Testing Theme", webapp, user1, files);
	private Idea i = new Idea(1, "Test Idea", "Testing Ideas", files, 0, t, user2);
//	String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MjY4NjA5ODEsImV4cCI6MTYyNjg2ODE4MSwidXNlcklkIjo0LCJlbWFpbElkIjoicmF0aGlyb2hhbjhAZ21haWwuY29tIiwibmFtZSI6IlJvaGFuIFJhdGhpIiwicHJpdmlsZWdlIjp7InByaXZpbGVnZUlkIjoxLCJwcml2aWxlZ2UiOiJDbGllbnQgUGFydG5lciJ9fQ.NOCopJfwqEaIZA9S9x_BeNSANjAd95g8vFfe2m_bI3M";
	
	@Autowired
	private MockMvc mockMvc;
	
	//creates JWT token
	private String generateJWTToken(Users user){
		long timestamp = System.currentTimeMillis();
		String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_KEY)
				.setIssuedAt(new Date(timestamp))
				.setExpiration(new Date(timestamp + Constants.TOKEN_VALIDAITY))
				.claim("userId", user.getUserId())
				.claim("emailId", user.getEmailId())
				.claim("name",user.getName())
				.claim("privilege", user.getPrivilege())
				.compact();
		return token;
	}
	
	@Test
	public void createThemeTest() throws Exception {
		when(usersUtil.getPrivilegeIdFromRequest(any())).thenReturn(user1.getPrivilege().getPrivilegeId());
		when(themeService.createTheme(t)).thenReturn(t);
		mockMvc.perform(post("/api/loggedin/themes")
				.header("Authorization", "Bearer " + this.generateJWTToken(user1))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content("{\"title\":\"Test Theme\", \"description\": \"Testing Theme\", \"category\": {\"categoryId\": 1}, \"user\": {\"userId\": 1}, \"files\": []}"))
				.andExpect(status().isOk());
	}
	
}
