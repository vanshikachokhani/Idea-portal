package com.psl.idea.service;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.tika.Tika;
import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartFile;

import com.psl.idea.models.Category;
import com.psl.idea.models.Theme;
import com.psl.idea.models.ThemeFiles;
import com.psl.idea.repository.CategoryRepository;
import com.psl.idea.repository.ThemeFileRepository;
import com.psl.idea.repository.ThemeRepo;

@Service
@EnableTransactionManagement
public class ThemeService {

	@Autowired
	ThemeRepo themeRepo;
	@Autowired
	ThemeFileRepository themeFileRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	DataSource dataSource;
	
	public Theme createTheme(Theme theme, MultipartFile[] multipartFiles) throws IOException, SQLException
	{
		Theme t = null;
		try(Connection connection = dataSource.getConnection()) {
			connection.setAutoCommit(false);
			t = themeRepo.save(theme);
			ThemeFiles themeFileToUpload;
			if(multipartFiles != null) {
				for(MultipartFile file: multipartFiles) {
					String fileName = theme.getUser().getUserId() + "." + theme.getThemeId() + "_" + file.getOriginalFilename();
					String filePath = ".\\data\\Themes\\" + fileName;
					Tika tika = new Tika();
					String fileType = tika.detect(fileName);
					file.transferTo(new File(filePath));
					themeFileToUpload = new ThemeFiles(fileName, fileType, t);
					themeFileToUpload = themeFileRepository.save(themeFileToUpload);
					if(themeFileToUpload == null)
					{
						connection.rollback();
						throw new TransactionException("Could not save file");
					}
				}
				
			}
			connection.commit();
		}
		return t;
		
	}
	 public List<Theme> viewThemes(){
			return themeRepo.findAll();
	}
	
	public List<Theme> getThemesByUser(long userId) {
		return themeRepo.findByUserUserId(userId);
	}
	public ThemeFiles getThemeFile(long themeId, long themeFileId) {
		ThemeFiles themeFile = themeFileRepository.findById(themeFileId).orElse(null);
		if(themeFile != null && themeFile.getTheme().getThemeId() == themeId)
			return themeFile;
		return null;
	}
	public Category getCategoryById(long categoryId) {
		return categoryRepository.findById(categoryId).orElse(null);
	}
	public Theme getThemeById(long themeId) {
		return themeRepo.findById(themeId).orElse(null);
	}
	public ThemeFiles[] getAllThemeFilesByTheme(long themeId) {
		return themeFileRepository.findAllByThemeThemeId(themeId);
	}
		
}
