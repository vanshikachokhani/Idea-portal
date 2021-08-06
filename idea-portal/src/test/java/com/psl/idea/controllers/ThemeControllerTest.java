package com.psl.idea.controllers;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.psl.idea.Constants;
import com.psl.idea.models.Category;
import com.psl.idea.models.Idea;
import com.psl.idea.models.Privilege;
import com.psl.idea.models.Theme;
import com.psl.idea.models.ThemeFiles;
import com.psl.idea.models.Users;
import com.psl.idea.repository.ThemeRepo;
import com.psl.idea.service.CategoryService;
import com.psl.idea.service.IdeaService;
import com.psl.idea.service.ThemeService;
import com.psl.idea.service.UserService;
import com.psl.idea.util.UsersUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@WebMvcTest(controllers = ThemeController.class)
@ExtendWith(SpringExtension.class)
public class ThemeControllerTest {

	@MockBean
	ThemeService themeService;
	@MockBean
	IdeaService ideaService;
	@MockBean
	UsersUtil usersUtil;
	@MockBean
	UserService userService;
	@MockBean
	ThemeRepo themeRepo;
	@MockBean
	DataSource dataSource;
	@MockBean
	Connection connection;
	@MockBean
	CategoryService categoryService;
	
	@Autowired
	WebApplicationContext webContext;

	private Privilege cpPrivilege = new Privilege(1, "Client Partner");
	private Privilege pmPrivilege = new Privilege(2, "Product Manager");
	private Category webapp = new Category(1, "WebApp");
	private Users user1 = new Users(1, "Rohan Rathi", "8830850720", "rathirohan8@gmail.com", "$2a$10$TdFsgRMeUDN3IEuKuryH4etBNpN7hY.iWMi83gzjskNMSLUQw7jJe", "Persistent", cpPrivilege);
	private Users user2 = new Users(2, "Rohan Rathi", "8830850720", "rathirohan8@gmail.com", "$2a$10$TdFsgRMeUDN3IEuKuryH4etBNpN7hY.iWMi83gzjskNMSLUQw7jJe", "Persistent", pmPrivilege);
	private Theme t = new Theme(1, "Test Theme", "Testing Theme", webapp, user1);
	private Idea i = new Idea(1, "Test Idea", "Testing Ideas", 0, t, user2);
	private MultipartFile[] files = {};
	private ThemeFiles[] themeFiles = {};
	private Idea[] ideas = {i};
//	String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MjY4NjA5ODEsImV4cCI6MTYyNjg2ODE4MSwidXNlcklkIjo0LCJlbWFpbElkIjoicmF0aGlyb2hhbjhAZ21haWwuY29tIiwibmFtZSI6IlJvaGFuIFJhdGhpIiwicHJpdmlsZWdlIjp7InByaXZpbGVnZUlkIjoxLCJwcml2aWxlZ2UiOiJDbGllbnQgUGFydG5lciJ9fQ.NOCopJfwqEaIZA9S9x_BeNSANjAd95g8vFfe2m_bI3M";
	
	@Autowired
	private MockMvc mockMvc;
	MockMultipartFile themeFile = new MockMultipartFile("files", "Test.png", MediaType.IMAGE_PNG_VALUE, new byte[2]);
	
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
		when(themeService.createTheme(t, files)).thenReturn(t);
		when(themeService.getCategoryById((long) 1)).thenReturn(webapp);
		when(userService.getUserByUserId((long) 1)).thenReturn(user1);
		
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
		mockMvc.perform(multipart("/api/loggedin/themes").file(themeFile).param("categoryId", "1").requestAttr("userId", 1).flashAttr("theme", t)
				.header("Authorization", "Bearer " + this.generateJWTToken(user1))
				.contentType(MediaType.MULTIPART_FORM_DATA))
				.andExpect(status().isOk());
		
		when(usersUtil.getPrivilegeIdFromRequest(any())).thenReturn(user2.getPrivilege().getPrivilegeId());
		
		mockMvc.perform(multipart("/api/loggedin/themes").file(themeFile).param("categoryId", "1").requestAttr("userId", 2).flashAttr("theme", t)
				.header("Authorization", "Bearer " + this.generateJWTToken(user2))
				.contentType(MediaType.MULTIPART_FORM_DATA))
				.andExpect(status().isUnauthorized());
		
	}
	
	@Test
	public void createIdeaTest() throws Exception {
		doReturn(i).when(ideaService).createIdea(any(Long.class), any(Idea.class), any(MultipartFile[].class));
		
		when(usersUtil.getPrivilegeIdFromRequest(any())).thenReturn(user2.getPrivilege().getPrivilegeId());
		when(userService.getUserByUserId(2)).thenReturn(user2);
		when(dataSource.getConnection()).thenReturn(connection);
		
		mockMvc.perform(multipart("/api/loggedin/themes/1/ideas").file(themeFile)
				.requestAttr("userId", 2)
				.flashAttr("idea", i)
				.header("Authorization", "Bearer " + this.generateJWTToken(user2))
				.contentType(MediaType.MULTIPART_FORM_DATA))
				.andExpect(status().isOk());
	}
	
	@Test
	public void getThemeDetailsTest() throws Exception {
		when(themeService.getThemeById(1)).thenReturn(t);
		when(themeService.getAllThemeFilesByTheme(1)).thenReturn(themeFiles);
		when(ideaService.getAllIdeasByTheme(1)).thenReturn(ideas);
		
		mockMvc.perform(get("/api/themes/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		when(themeService.getThemeById(9)).thenReturn(null);
		
		mockMvc.perform(get("/api/themes/9")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void getCategoriesTest() throws Exception {
		List<Category> categories = new ArrayList<>();
		categories.add(webapp);
		when(categoryService.getAllCategories()).thenReturn(categories);
		
		mockMvc.perform(get("/api/categories"))
		.andExpect(status().isOk());
	}
	
}
