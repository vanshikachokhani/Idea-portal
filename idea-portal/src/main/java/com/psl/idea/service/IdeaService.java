package com.psl.idea.service;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.tika.Tika;
import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartFile;

import com.psl.idea.models.Idea;
import com.psl.idea.models.IdeaFiles;
import com.psl.idea.models.Theme;
import com.psl.idea.repository.IdeaFilesRepository;
import com.psl.idea.repository.IdeaRepo;
import com.psl.idea.repository.IdeaRepoImpl;
import com.psl.idea.repository.ThemeRepo;

@Service
@EnableTransactionManagement
public class IdeaService{
		
	@Autowired
	ThemeRepo themeRepo;
	@Autowired
	DataSource dataSource;
	@Autowired
	IdeaRepo ideaRepo;
	@Autowired
	IdeaFilesRepository ideaFilesRepository;
	@Autowired
	IdeaRepoImpl ideaRepoImpl;

	public List<Theme> viewThemes(){
		return 	themeRepo.findAll();
	}

	public List<Idea> viewIdeas(long themeID){
		return ideaRepoImpl.findAll(themeID);
	}


	// sort by most likes
	public List<Idea> viewIdeasbyLikes(long themeID){
		return ideaRepoImpl.findbylike(themeID);
	}

	// sort by newest first
	public List<Idea> viewIdeasbyDate(long themeID){
		return ideaRepoImpl.findbydate(themeID);
	}

	//sort by most commented first
	public List<Idea> viewIdeasbyComment(long themeID){
		return ideaRepo.findbycomment(themeID);
	}

	public Idea createIdea(long themeId, Idea idea, MultipartFile[] multipartFiles) throws IOException, TransactionException {
		Theme t = themeRepo.findById(themeId).orElse(null);
		if(t != null)
		{
			Idea i = null;
			try(Connection connection = dataSource.getConnection()) {
				connection.setAutoCommit(false);
				idea.setTheme(t);
				i = ideaRepo.save(idea);
				IdeaFiles ideaFileToUpload;
				if(multipartFiles != null)
				{
					for(MultipartFile file: multipartFiles) {
						String fileName = idea.getUser().getUserId() + "." + idea.getIdeaId() + "_" + file.getOriginalFilename();
						String filePath = "E:\\Persistent\\data\\Ideas\\" + fileName;
						Tika tika = new Tika();
						String fileType = tika.detect(fileName);
						file.transferTo(new File(filePath));
						ideaFileToUpload = new IdeaFiles(fileName, fileType, i);
						if(ideaFilesRepository.save(ideaFileToUpload) == null)
							throw new TransactionException("Could not save file");
					}
				}
				return i;
			} catch(SQLException sql) {
			}
		}
		return null;
	}
	
	public IdeaFiles getIdeaFile(long ideaId, long ideaFileId) {
		IdeaFiles ideaFile = ideaFilesRepository.findById(ideaFileId).orElse(null);
		if(ideaFile != null && ideaFile.getIdea().getIdeaId() == ideaId)
			return ideaFile;
		return null;
	}

	public List<Idea> getIdeasByUser(long userId) {
		List<Idea> ideas = ideaRepo.findByUserUserId(userId);
		
		return ideas;
	}

	public Idea[] getAllIdeasByTheme(long themeId) {
		return ideaRepo.findAllByThemeThemeId(themeId);
	}

}
