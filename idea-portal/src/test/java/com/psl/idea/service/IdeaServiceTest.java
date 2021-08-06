package com.psl.idea.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import com.psl.idea.models.Category;
import com.psl.idea.models.Idea;
import com.psl.idea.models.IdeaFiles;
import com.psl.idea.models.Privilege;
import com.psl.idea.models.Theme;
import com.psl.idea.models.Users;
import com.psl.idea.repository.IdeaFilesRepository;
import com.psl.idea.repository.IdeaRepo;
import com.psl.idea.repository.IdeaRepoImpl;
import com.psl.idea.repository.ThemeRepo;

@WebMvcTest(controllers=IdeaService.class)
@ExtendWith(SpringExtension.class)
public class IdeaServiceTest {
	
	@MockBean
	ThemeRepo themeRepo;
	@MockBean
	IdeaRepo ideaRepo;
	@MockBean
	DataSource dataSource;
	@MockBean
	IdeaFilesRepository ideaFilesRepository;
	@MockBean
	IdeaRepoImpl ideaRepoImpl;
	@MockBean
	Connection connection;
	
	@Autowired
	IdeaService ideaService;
	@Autowired
	MockMvc mockMvc;
	
	MultipartFile[] files = {};
	Privilege cpPrivilege = new Privilege(1, "Client Partner");
	Category category = new Category(1, "WebApp");
	Users user = new Users(1, "Rohan Rathi", "8830850720", "rohan_rathi@persistent.com", "RohanRathi", "Persistent", cpPrivilege);
	Theme theme = new Theme(1, "Test Theme", "Testing Theme", category, user);
	Idea idea = new Idea(1, "Test Idea", "Testing Theme", 0, theme, user);
	IdeaFiles ideaFile = new IdeaFiles("1.1_Test.txt", "application/text", idea);
	
	@Test
	public void createIdeaServiceTest() throws Exception {
		Optional<Theme> optionalTheme = Optional.of(theme);
		when(themeRepo.findById(theme.getThemeId())).thenReturn(optionalTheme);
		when(ideaRepo.save(idea)).thenReturn(idea);
		
		optionalTheme = Optional.empty();
		when(themeRepo.findById((long) 2)).thenReturn(optionalTheme);
		when(dataSource.getConnection()).thenReturn(connection);

		try {
			assertEquals(idea, ideaService.createIdea(1, idea, files));
			
			assertEquals(null, ideaService.createIdea(2, idea, files));
		} catch(IOException io) {
			fail();
			io.printStackTrace();
		}
		
		verify(themeRepo).findById(theme.getThemeId());
		verify(ideaRepo).save(idea);
	}
	
	@Test
	public void getIdeaFileTest() {
		when(ideaFilesRepository.findById((long) 1)).thenReturn(Optional.of(ideaFile));
		when(ideaFilesRepository.findById((long) 2)).thenReturn(Optional.empty());
		
		assertEquals(ideaFile, ideaService.getIdeaFile(1, 1));
		assertEquals(null, ideaService.getIdeaFile(1, 2));
		assertEquals(null, ideaService.getIdeaFile(2, 1));
		
		verify(ideaFilesRepository, times(2)).findById((long) 1);
		verify(ideaFilesRepository).findById((long) 2);
	}
	
	@Test
	public void getIdeasByUserTest() {
		List<Idea> ideas = new ArrayList<>();
		ideas.add(idea);
		
		when(ideaRepo.findByUserUserId((long) 1)).thenReturn(ideas);
		
		assertEquals(ideas, ideaService.getIdeasByUser((long) 1));
		
		verify(ideaRepo).findByUserUserId((long) 1);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void getAllIdeasByThemeTest() {
		Idea[] ideas = {idea};
		when(ideaRepo.findAllByThemeThemeId((long) 1)).thenReturn(ideas);
		when(ideaRepo.findAllByThemeThemeId((long) 2)).thenReturn(null);
		
		assertEquals(ideas, ideaService.getAllIdeasByTheme((long) 1));
		assertEquals(null, ideaService.getAllIdeasByTheme((long) 2));
		
		verify(ideaRepo).findAllByThemeThemeId((long) 1);
		verify(ideaRepo).findAllByThemeThemeId((long) 2);
	}

}
