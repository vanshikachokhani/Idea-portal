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
import com.psl.idea.models.Privilege;
import com.psl.idea.models.Theme;
import com.psl.idea.models.Users;
import com.psl.idea.repository.CommentRepo;
import com.psl.idea.repository.IdeaRepo;
import com.psl.idea.repository.ParticipantRepo;
import com.psl.idea.repository.RatingRepo;
import com.psl.idea.repository.ThemeRepo;
import com.psl.idea.repository.UserRepo;
import com.psl.idea.service.CommentService;

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
	
	@Test
	public void getUserThemesTest() throws Exception {
		String[] files = {""};
		List<Theme> themes = new ArrayList<>();
		Theme t = new Theme(1, "Test Theme", "Testing Theme", new Category(1, "WebApp"),
				new Users(1, "Rohan Rathi", "8830850720", "rathirohan8@gmail.com", "$2a$10$TdFsgRMeUDN3IEuKuryH4etBNpN7hY.iWMi83gzjskNMSLUQw7jJe", new Privilege(1, "Client Partner")), files);
		themes.add(t);
		when(themeRepo.findByUserUserId(1)).thenReturn(themes);
		when(userRepo.findByuserId(1)).thenReturn(new Users(1, "Rohan Rathi", "8830850720", "rathirohan8@gmail.com", "$2a$10$TdFsgRMeUDN3IEuKuryH4etBNpN7hY.iWMi83gzjskNMSLUQw7jJe", new Privilege(1, "Client Partner")));
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/loggedin/users/1/themes")
				.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MjY4NTEzNzgsImV4cCI6MTYyNjg1ODU3OCwidXNlcklkIjo0LCJlbWFpbElkIjoicmF0aGlyb2hhbjhAZ21haWwuY29tIiwibmFtZSI6IlJvaGFuIFJhdGhpIiwicHJpdmlsZWdlIjp7InByaXZpbGVnZUlkIjoxLCJwcml2aWxlZ2UiOiJDbGllbnQgUGFydG5lciJ9fQ.IQ7xjBkJOprB1yYcPl6kOejHTpwyKMJuLBDw0IqpFTA"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title").value("Test Theme"))
				.andExpect(jsonPath("$[0].themeId").value(1))
				.andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		
		when(themeRepo.findByUserUserId(2)).thenReturn(new ArrayList<Theme>());
		when(userRepo.findByuserId(2)).thenReturn(new Users(2, "Rohan Rathi", "8830850720", "rathirohan8@gmail.com", "$2a$10$TdFsgRMeUDN3IEuKuryH4etBNpN7hY.iWMi83gzjskNMSLUQw7jJe", new Privilege(1, "Client Partner")));
		
		result = mockMvc.perform(MockMvcRequestBuilders.get("/api/loggedin/users/2/themes")
				.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MjY4NTEzNzgsImV4cCI6MTYyNjg1ODU3OCwidXNlcklkIjo0LCJlbWFpbElkIjoicmF0aGlyb2hhbjhAZ21haWwuY29tIiwibmFtZSI6IlJvaGFuIFJhdGhpIiwicHJpdmlsZWdlIjp7InByaXZpbGVnZUlkIjoxLCJwcml2aWxlZ2UiOiJDbGllbnQgUGFydG5lciJ9fQ.IQ7xjBkJOprB1yYcPl6kOejHTpwyKMJuLBDw0IqpFTA"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isEmpty())
				.andReturn();
		
	}
	
}
