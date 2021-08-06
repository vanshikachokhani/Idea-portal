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

import org.apache.tika.Tika;
import org.hibernate.TransactionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import com.psl.idea.models.Category;
import com.psl.idea.models.Privilege;
import com.psl.idea.models.Theme;
import com.psl.idea.models.ThemeFiles;
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
	@MockBean
	Tika tika;
	
	@Autowired
	ThemeService themeService;
	
	@Autowired
	MockMvc mockMvc;
	
	MultipartFile multipartFile1 = new MockMultipartFile("Test.txt", new byte[100]);
	MultipartFile multipartFile2 = new MockMultipartFile("Test.png", new byte[100]);
	MultipartFile[] files = {multipartFile1};
	Privilege cpPrivilege = new Privilege(1, "Client Partner");
	Category category = new Category(1, "WebApp");
	Users user = new Users(1, "Rohan Rathi", "8830850720", "rohan_rathi@persistent.com", "RohanRathi", "Persistent", cpPrivilege);
	Theme theme = new Theme(1, "Test Theme", "Testing Theme", category, user);
	ThemeFiles themeFile = new ThemeFiles("1.1_Test.txt", "application/text", theme);
	
	@Test
	public void createThemeTest() throws Exception {
		String fileName = theme.getUser().getUserId() + "." + theme.getThemeId() + "_" + multipartFile1.getOriginalFilename();
		themeFile = new ThemeFiles(fileName, "application/text", theme);
		MultipartFile[] newFiles = {multipartFile2};
		ThemeFiles newThemeFile = new ThemeFiles("Test.png", "image/png", theme);
		
		when(themeRepo.save(theme)).thenReturn(theme);
		when(dataSource.getConnection()).thenReturn(connection);
		when(tika.detect("Test.txt")).thenReturn("application/text");
		when(tika.detect("Test.png")).thenReturn("image/png");
		when(themeFilesRepository.save(themeFile)).thenReturn(themeFile);
		when(themeFilesRepository.save(newThemeFile)).thenReturn(null);
		
		try {
			assertEquals(theme, themeService.createTheme(theme, files));
		} catch(IOException io) {
			fail();
			io.printStackTrace();
		} catch(TransactionException t) {
			fail();
			t.printStackTrace();
		}
		
		try {
			assertEquals(theme, themeService.createTheme(theme, null));
		} catch(IOException io) {
			fail();
			io.printStackTrace();
		}
		
		try {
			assertEquals(theme, themeService.createTheme(theme, newFiles));
		} catch(IOException io) {
			fail();
			io.printStackTrace();
		} catch(TransactionException t) {
			assertEquals("Could not save file", t.getMessage());
		}
		
		
		verify(themeRepo, times(3)).save(theme);
		
	}
	
	@Test
	public void getThemesByUserTest() {
		List<Theme> themes = new ArrayList<>();
		themes.add(theme);
		
		when(themeRepo.findByUserUserId((long) 1)).thenReturn(themes);
		
		assertEquals(themes, themeService.getThemesByUser((long) 1));
		
		verify(themeRepo).findByUserUserId((long) 1);
	}
	
	@Test
	public void viewThemesTest() {
		List<Theme> themes = new ArrayList<>();
		themes.add(theme);
		
		when(themeRepo.findAll()).thenReturn(themes);
		
		assertEquals(themes, themeService.viewThemes());
		
		verify(themeRepo).findAll();
	}
	
	@Test
	public void getThemeFileTest() {
		when(themeFilesRepository.findById((long) 1)).thenReturn(Optional.of(themeFile));
		when(themeFilesRepository.findById((long) 2)).thenReturn(Optional.empty());
		
		assertEquals(themeFile, themeService.getThemeFile(1, 1));
		assertEquals(null, themeService.getThemeFile(1, 2));
		assertEquals(null, themeService.getThemeFile(2, 1));
		
		verify(themeFilesRepository, times(2)).findById((long) 1);
		verify(themeFilesRepository).findById((long) 2);
		
	}
	
	@Test
	public void getCategoryByIdTest() {
		when(categoryRepository.findById((long) 1)).thenReturn(Optional.of(category));
		when(categoryRepository.findById((long) 2)).thenReturn(Optional.empty());
		
		assertEquals(category, themeService.getCategoryById(1));
		assertEquals(null, themeService.getCategoryById(2));
		
		verify(categoryRepository).findById((long) 1);
		verify(categoryRepository).findById((long) 2);
	}
	
	@Test
	public void getThemeByIdTest() {
		when(themeRepo.findById((long) 1)).thenReturn(Optional.of(theme));
		when(themeRepo.findById((long) 2)).thenReturn(Optional.empty());
		
		assertEquals(theme, themeService.getThemeById((long) 1));
		assertEquals(null, themeService.getThemeById((long) 2));
		
		verify(themeRepo).findById((long) 1);
		verify(themeRepo).findById((long) 2);
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void getAllThemeFilesByThemeTest() {
		ThemeFiles[] themeFiles = {themeFile};
		when(themeFilesRepository.findAllByThemeThemeId((long) 1)).thenReturn(themeFiles);
		when(themeFilesRepository.findAllByThemeThemeId((long) 2)).thenReturn(null);
		
		assertEquals(themeFiles, themeService.getAllThemeFilesByTheme((long) 1));
		assertEquals(null, themeService.getAllThemeFilesByTheme((long) 2));
		
		verify(themeFilesRepository).findAllByThemeThemeId((long) 1);
		verify(themeFilesRepository).findAllByThemeThemeId((long) 2);
	}

}
