package com.psl.idea.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.psl.idea.models.Category;
import com.psl.idea.models.Privilege;
import com.psl.idea.models.Theme;
import com.psl.idea.models.Users;
import com.psl.idea.repository.ThemeRepo;

@WebMvcTest(controllers=ThemeService.class)
@ExtendWith(SpringExtension.class)
public class ThemeServiceTests {
	
	@MockBean
	ThemeRepo themeRepo;
	
	@Autowired
	ThemeService themeService;
	
	@Autowired
	MockMvc mockMvc;
	
	String[] files = {};
	Privilege cpPrivilege = new Privilege(1, "Client Partner");
	Category category = new Category(1, "WebApp");
	Users user = new Users(1, "Rohan Rathi", "8830850720", "rohan_rathi@persistent.com", "RohanRathi", "Persistent", cpPrivilege);
	Theme theme = new Theme(1, "Test Theme", "Testing Theme", category, user, files);
	
	@Test
	public void createThemeTest() {
		when(themeRepo.save(theme)).thenReturn(theme);
		
		assertEquals(theme, themeService.createTheme(theme));
		
		verify(themeRepo).save(theme);
		
	}

}
