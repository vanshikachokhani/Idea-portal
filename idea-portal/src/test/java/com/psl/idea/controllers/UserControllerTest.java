package com.psl.idea.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.psl.idea.util.UsersUtil;

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
	@MockBean
	UsersUtil usersUtil;
	@MockBean
	HttpServletRequest httpServletRequest;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Privilege cpPrivilege = new Privilege(1, "Client Partner");
	private Category webapp = new Category(1, "WebApp");
	private Users user1 = new Users(1, "Rohan Rathi", "8830850720", "rathirohan8@gmail.com", "$2a$10$TdFsgRMeUDN3IEuKuryH4etBNpN7hY.iWMi83gzjskNMSLUQw7jJe", "Persistent", cpPrivilege);
	private Users user2 = new Users(2, "Rohan Rathi", "8830850720", "rathirohan8@gmail.com", "$2a$10$TdFsgRMeUDN3IEuKuryH4etBNpN7hY.iWMi83gzjskNMSLUQw7jJe", "Persistent", cpPrivilege);
	private Theme t = new Theme(1, "Test Theme", "Testing Theme", webapp, user1);
	private Idea i = new Idea(1, "Test Idea", "Testing Ideas", 0, t, user2);
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

		when(usersUtil.getPrivilegeIdFromRequest(any())).thenReturn((long) 1);
		when(httpServletRequest.getAttribute("userId")).thenReturn(1);
		
		when(themeService.getThemesByUser(1)).thenReturn(themes);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/loggedin/users/1/themes")
				.header("Authorization", "Bearer " + this.generateJWTToken(user1)))
				.andExpect(status().isOk());
		
		when(themeService.getThemesByUser(2)).thenReturn(new ArrayList<Theme>());
		mockMvc.perform(MockMvcRequestBuilders.get("/api/loggedin/users/1/themes")
				.header("Authorization", "Bearer " + this.generateJWTToken(user2)))
				.andExpect(status().isUnauthorized());
		
	}
	
	@Test
	public void getUserIdeasTest() throws Exception {
		List<Idea> ideas = new ArrayList<>();
		ideas.add(i);
		
		when(usersUtil.getPrivilegeIdFromRequest(any())).thenReturn((long) 2);
		
		when(ideaService.getIdeasByUser(2)).thenReturn(ideas);
		when(httpServletRequest.getAttribute("userId")).thenReturn(2);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/loggedin/users/2/ideas")
				.header("Authorization", "Bearer " + generateJWTToken(user2)))
				.andExpect(status().isOk());
		
		when(ideaService.getIdeasByUser(1)).thenReturn(new ArrayList<Idea>());
		mockMvc.perform(MockMvcRequestBuilders.get("/api/loggedin/users/1/ideas")
				.header("Authorization", "Bearer " + generateJWTToken(user2)))
		.andExpect(status().isUnauthorized())
		.andReturn();
	}
	
	@Test
	public void registerUserTest() throws Exception {
		String jsonString="{\"emailId\":\"johndoe@gmail.com\", \"name\": \"John Doe\", \"password\":\"123456\" ,\"phoneNumber\":\"9423160789\" , \"privilege\": {\"privilegeId\": 1}}";
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/users/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	public void loginUserTest() throws Exception {
		String jsonString="{\"emailId\":\"johndoe@gmail.com\", \"password\":\"123456\"}";
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void updateEmailIdAndCompanyTest() throws Exception{
		String updateEmailAndCompany ="{\"oldemailId\":\"johndoe@gmail.com\", \"newemailId\": \"johndoe@gmail.com\", \"password\":\"123456\" ,\"oldcompany\":\"Test Compnay\" , \"newcompany\":\"Testing \"}";
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/users/update-emailId-company")
				.contentType(MediaType.APPLICATION_JSON)
				.content(updateEmailAndCompany))
				.andExpect(MockMvcResultMatchers.status().isOk());
		
		String updateEmail = "{\"oldemailId\":\"johndoe@gmail.com\", \"newemailId\": \"johndoe@gmail.com\", \"password\":\"123456\"}"; 
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/users/update-emailId-company")
				.contentType(MediaType.APPLICATION_JSON)
				.content(updateEmail))
				.andExpect(MockMvcResultMatchers.status().isOk());
	
		String updateCompany ="{\"oldcompany\":\"Test Compnay\" , \"newcompany\":\"Testing \",\"password\":\"123456\"}";
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/users/update-emailId-company")
				.contentType(MediaType.APPLICATION_JSON)
				.content(updateCompany))
				.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	@Test
	public void updatePasswordTest() throws Exception {
		String updatePassword = "{\"emailId\":\"johndoe@gmail.com\", \"oldpassword\": \"12345678\", \"newpassword\":\"qwertyuiop\"}"; 
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/users/update-password")
				.contentType(MediaType.APPLICATION_JSON)
				.content(updatePassword))
				.andExpect(MockMvcResultMatchers.status().isOk());		
	}

//	@Test
//	public void 
}
