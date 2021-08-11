package com.psl.idea.models;

import org.springframework.stereotype.Component;

@Component
public class ThemeDetails {
	
	private Theme theme;
	private ThemeFiles[] themeFiles;
	private Idea[] ideas;
	
	public ThemeDetails() {
		super();
	}

	public ThemeDetails(Theme theme, ThemeFiles[] themeFiles, Idea[] ideas) {
		super();
		this.theme = theme;
		this.themeFiles = themeFiles;
		this.ideas = ideas;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public ThemeFiles[] getThemeFiles() {
		return themeFiles;
	}

	public void setThemeFiles(ThemeFiles[] themeFiles) {
		this.themeFiles = themeFiles;
	}

	public Idea[] getIdeas() {
		return ideas;
	}

	public void setIdeas(Idea[] ideas) {
		this.ideas = ideas;
	}

	@Override
	public String toString() {
		return "Theme Details [Theme=" + theme.getThemeId() + "]";
	}

}
