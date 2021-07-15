package com.psl.idea.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psl.idea.models.Theme;
import com.psl.idea.repository.ThemeRepo;

@Service
public class ThemeService {

	@Autowired
	ThemeRepo themeRepo;
	
	public void createTheme(Theme theme)
	{
		theme.setUser(null);
		themeRepo.save(theme);
	}
	 public List<Theme> viewThemes(){
			return themeRepo.findAll();
		}
		
}
