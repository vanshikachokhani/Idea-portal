package com.psl.idea.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.Connection;

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
import com.psl.idea.models.Privilege;
import com.psl.idea.models.Theme;
import com.psl.idea.models.Users;
import com.psl.idea.repository.CategoryRepository;
import com.psl.idea.repository.ThemeFileRepository;
import com.psl.idea.repository.ThemeRepo;

@WebMvcTest(controllers=ThemeService.class)
@ExtendWith(SpringExtension.class)
public class ThemeServiceTests {
	
	@MockBean
	ThemeRepo themeRepo;
	@MockBean
	ThemeFileRepository themeFilesRepository;
	@MockBean
	CategoryRepository categoryRepository;
	@MockBean
	DataSource dataSource;
	@MockBean
	Connection connection;
	
	@Autowired
	ThemeService themeService;
	
	@Autowired
	MockMvc mockMvc;
	
	MultipartFile[] files = {};
	Privilege cpPrivilege = new Privilege(1, "Client Partner");
	Category category = new Category(1, "WebApp");
	Users user = new Users(1, "Rohan Rathi", "8830850720", "rohan_rathi@persistent.com", "RohanRathi", "Persistent", cpPrivilege);
	Theme theme = new Theme(1, "Test Theme", "Testing Theme", category, user);
	
	@Test
	public void createThemeTest() throws Exception {
		when(themeRepo.save(theme)).thenReturn(theme);
		when(dataSource.getConnection()).thenReturn(connection);
		try {
			assertEquals(theme, themeService.createTheme(theme, files));
		} catch(IOException io) {
			fail();
			io.printStackTrace();
		}
		
		verify(themeRepo).save(theme);
		
	}

}
