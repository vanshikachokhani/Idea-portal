package com.psl.idea.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.psl.idea.Constants;
import com.psl.idea.models.Category;
import com.psl.idea.models.Idea;
import com.psl.idea.models.Privilege;
import com.psl.idea.models.Theme;
import com.psl.idea.models.Users;
import com.psl.idea.service.IdeaService;
import com.psl.idea.service.ThemeService;
import com.psl.idea.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
	
	@MockBean
	UserService userService;
	@MockBean
	ThemeService themeService;
	@MockBean
	IdeaService ideaService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Privilege cpPrivilege = new Privilege(1, "Client Partner");
	private Category webapp = new Category(1, "WebApp");
	private Users user1 = new Users(1, "Rohan Rathi", "8830850720", "rathirohan8@gmail.com", "$2a$10$TdFsgRMeUDN3IEuKuryH4etBNpN7hY.iWMi83gzjskNMSLUQw7jJe", cpPrivilege);
	private Users user2 = new Users(2, "Rohan Rathi", "8830850720", "rathirohan8@gmail.com", "$2a$10$TdFsgRMeUDN3IEuKuryH4etBNpN7hY.iWMi83gzjskNMSLUQw7jJe", cpPrivilege);
	private String[] files = {};
	private Theme t = new Theme(1, "Test Theme", "Testing Theme", webapp, user1, files);
	private Idea i = new Idea(1, "Test Idea", "Testing Ideas", files, 0, t, user2);
//	private String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MjY4NTEzNzgsImV4cCI6MTYyNjg1ODU3OCwidXNlcklkIjo0LCJlbWFpbElkIjoicmF0aGlyb2hhbjhAZ21haWwuY29tIiwibmFtZSI6IlJvaGFuIFJhdGhpIiwicHJpdmlsZWdlIjp7InByaXZpbGVnZUlkIjoxLCJwcml2aWxlZ2UiOiJDbGllbnQgUGFydG5lciJ9fQ.IQ7xjBkJOprB1yYcPl6kOejHTpwyKMJuLBDw0IqpFTA";
	
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
	public void getUserThemesTest() throws Exception {
		List<Theme> themes = new ArrayList<>();
		themes.add(t);
		
		when(themeService.getThemesByUser(1)).thenReturn(themes);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/loggedin/users/1/themes")
				.header("Authorization", "Bearer " + this.generateJWTToken(user1)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title").value("Test Theme"))
				.andExpect(jsonPath("$[0].themeId").value(1))
				.andReturn();
		
		when(themeService.getThemesByUser(2)).thenReturn(new ArrayList<Theme>());
		mockMvc.perform(MockMvcRequestBuilders.get("/api/loggedin/users/2/themes")
				.header("Authorization", "Bearer " + this.generateJWTToken(user1)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isEmpty())
				.andReturn();
		
	}
	
	@Test
	public void getUserIdeasTest() throws Exception {
		List<Idea> ideas = new ArrayList<>();
		ideas.add(i);
		
		when(ideaService.getIdeasByUser(2)).thenReturn(ideas);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/loggedin/users/2/ideas")
				.header("Authorization", "Bearer " + generateJWTToken(user2)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].ideaId").value(1))
				.andExpect(jsonPath("$[0].title").value("Test Idea"))
				.andExpect(jsonPath("$[0].user.userId").value(2));
		
		when(ideaService.getIdeasByUser(1)).thenReturn(new ArrayList<Idea>());
		mockMvc.perform(MockMvcRequestBuilders.get("/api/loggedin/users/1/ideas")
				.header("Authorization", "Bearer " + generateJWTToken(user1)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").isEmpty())
		.andReturn();
	}
	
	@Test
	public void registerUserTest() throws Exception {
		String jsonString="{\"emailId\":\"johndoe@gmail.com\", \"name\": \"John Doe\", \"password\":\"123456\" ,\"phoneNumber\":\"9423160789\" , \"privilege\": {\"privilegeId\": 1}}";
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/users/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.emailId", Matchers.is("johndoe@gmail.com")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("John Doe")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.password", Matchers.is("123456")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", Matchers.is("9423160789")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.privilege.privilegeId", Matchers.is(1)))
				.andReturn();
	}
	
	public void loginUserTest() throws Exception {
		String jsonString="{\"emailId\":\"johndoe@gmail.com\", \"password\":\"123456\"}";
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.emailId", Matchers.is("johndoe@gmail.com")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.password", Matchers.is("123456")))
				.andReturn();
	}

}
