package com.psl.idea.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psl.idea.models.Theme;
import com.psl.idea.repository.ThemeRepo;

@Service
public class ThemeService {

	@Autowired
	ThemeRepo themeRepo;
	
	public Theme createTheme(Theme theme)
	{
		return themeRepo.save(theme);
		
	}
	 public List<Theme> viewThemes(){
			return themeRepo.findAll();
	}
	
	public List<Theme> getThemesByUser(long userId) {
		List<Theme> themes = new ArrayList<>();
		
		themes = themeRepo.findByUserUserId(userId);
		
		return themes;
	}
		
}
