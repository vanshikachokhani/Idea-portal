package com.psl.idea;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.psl.idea.models.Category;
import com.psl.idea.models.Idea;
import com.psl.idea.models.Privilege;
import com.psl.idea.models.Theme;
import com.psl.idea.models.Users;
import com.psl.idea.repository.CommentRepo;
import com.psl.idea.repository.IdeaRepo;
import com.psl.idea.repository.ParticipantRepo;
import com.psl.idea.repository.RatingRepo;
import com.psl.idea.repository.ThemeRepo;
import com.psl.idea.repository.UserRepo;

@ComponentScan
@WebMvcTest
@AutoConfigureMockMvc
@ContextConfiguration
public class UserControllerTest {

	@MockBean
	private ThemeRepo themeRepo;
	@MockBean
	private UserRepo userRepo;
	@MockBean
	private CommentRepo commentRepo;
	@MockBean
	private IdeaRepo ideaRepo;
	@MockBean
	private ParticipantRepo participantRepo;
	@MockBean
	private RatingRepo ratingRepo;
	@MockBean
	private SessionFactory sessionFactory;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Privilege cpPrivilege = new Privilege(1, "Client Partner");
	private Category webapp = new Category(1, "WebApp");
	private Users user1 = new Users(1, "Rohan Rathi", "8830850720", "rathirohan8@gmail.com", "$2a$10$TdFsgRMeUDN3IEuKuryH4etBNpN7hY.iWMi83gzjskNMSLUQw7jJe", cpPrivilege);
	private Users user2 = new Users(2, "Rohan Rathi", "8830850720", "rathirohan8@gmail.com", "$2a$10$TdFsgRMeUDN3IEuKuryH4etBNpN7hY.iWMi83gzjskNMSLUQw7jJe", cpPrivilege);
	private String[] files = {};
	private Theme t = new Theme(1, "Test Theme", "Testing Theme", webapp, user1, files);
	private Idea i = new Idea(1, "Test Idea", "Testing Ideas", files, 0, t, user2);
	private String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MjY4NTEzNzgsImV4cCI6MTYyNjg1ODU3OCwidXNlcklkIjo0LCJlbWFpbElkIjoicmF0aGlyb2hhbjhAZ21haWwuY29tIiwibmFtZSI6IlJvaGFuIFJhdGhpIiwicHJpdmlsZWdlIjp7InByaXZpbGVnZUlkIjoxLCJwcml2aWxlZ2UiOiJDbGllbnQgUGFydG5lciJ9fQ.IQ7xjBkJOprB1yYcPl6kOejHTpwyKMJuLBDw0IqpFTA";
	
	@Test
	public void getUserThemesTest() throws Exception {
		List<Theme> themes = new ArrayList<>();
		themes.add(t);
		when(themeRepo.findByUserUserId(1)).thenReturn(themes);
		when(userRepo.findByuserId(1)).thenReturn(user1);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/loggedin/users/1/themes")
				.header("Authorization", "Bearer " + jwtToken))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title").value("Test Theme"))
				.andExpect(jsonPath("$[0].themeId").value(1))
				.andReturn();
		
		when(themeRepo.findByUserUserId(2)).thenReturn(new ArrayList<Theme>());
		when(userRepo.findByuserId(2)).thenReturn(user2);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/loggedin/users/2/themes")
				.header("Authorization", "Bearer " + jwtToken))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isEmpty())
				.andReturn();
		
	}
	
	@Test
	public void getUserIdeasTest() throws Exception {
		List<Idea> ideas = new ArrayList<>();
		ideas.add(i);
		when(ideaRepo.findByUserUserId(2)).thenReturn(ideas);
		when(userRepo.findByuserId(2)).thenReturn(user2);
		when(ideaRepo.findByUserUserId(1)).thenReturn(new ArrayList<Idea>());
		when(userRepo.findByuserId(1)).thenReturn(user1);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/loggedin/users/2/ideas")
				.header("Authorization", "Bearer " + jwtToken))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].ideaId").value(1))
		.andExpect(jsonPath("$[0].title").value("Test Idea"))
		.andExpect(jsonPath("$[0].user.userId").value(2))
		.andReturn();
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/loggedin/users/1/ideas")
				.header("Authorization", "Bearer " + jwtToken))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").isEmpty())
		.andReturn();
	}
	
}
