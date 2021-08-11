package com.psl.idea.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.psl.idea.exceptions.NotFoundException;
import com.psl.idea.exceptions.UnauthorizedException;
import com.psl.idea.models.Category;
import com.psl.idea.models.Idea;
import com.psl.idea.models.Theme;
import com.psl.idea.models.ThemeDetails;
import com.psl.idea.models.ThemeFiles;
import com.psl.idea.models.Users;
import com.psl.idea.service.CategoryService;
import com.psl.idea.service.IdeaService;
import com.psl.idea.service.ThemeService;
import com.psl.idea.service.UserService;
import com.psl.idea.util.FilesUtil;
import com.psl.idea.util.UsersUtil;


@CrossOrigin(origins="*")
@RestController
@RequestMapping()
public class ThemeController {
	@Autowired
	private IdeaService ideaService;
	@Autowired
	private ThemeService themeService;
	@Autowired
	private UsersUtil usersUtil;
	@Autowired
	private UserService userService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private FilesUtil filesUtil;
	
     // view all themes
	@GetMapping(path="/api/themes")
    public List<Theme> viewThemes(){
		return themeService.viewThemes();
	}
	
	
	// view all ideas of a particular theme
	@GetMapping(path="/api/themes/{themeID}/ideas")
    public List<Idea> viewIdeas(@PathVariable("themeID") long themeID){
		return ideaService.viewIdeas(themeID);
	}
	
	
	// sort by most likes
	@GetMapping(path="/api/themes/{themeID}/ideas/likes")
    public List<Idea> viewIdeasbyLikes(@PathVariable("themeID") long themeID){
		return ideaService.viewIdeasbyLikes(themeID);
	}
	
	// sort by newest first
	@GetMapping(path="/api/themes/{themeID}/ideas/date")
    public List<Idea> viewIdeasbyDate(@PathVariable("themeID") long themeID){
		return ideaService.viewIdeasbyDate(themeID);
	}
	
	//sort by most commented first
	@GetMapping(path="/api/themes/{themeID}/ideas/comment")
    public List<Idea> viewIdeasbyComment(@PathVariable("themeID") long themeID){
		return ideaService.viewIdeasbyComment(themeID);
	}
	
	@GetMapping(path="/api/categories")
	public ResponseEntity<List<Category>> getCategories() {
		List<Category> categories = categoryService.getAllCategories();
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}
	
	@GetMapping(path="/api/themes/{themeId}")
	public ResponseEntity<ThemeDetails> getThemeDetails(@PathVariable("themeId") long themeId) throws NotFoundException {
		Theme theme = themeService.getThemeById(themeId);
		if(theme == null)
			throw new NotFoundException("Theme Not Found");
		ThemeFiles[] themeFiles = themeService.getAllThemeFilesByTheme(themeId);
		Idea[] ideas = ideaService.getAllIdeasByTheme(themeId);
		ThemeDetails themeDetails = new ThemeDetails(theme, themeFiles, ideas);
		return new ResponseEntity<>(themeDetails, HttpStatus.OK);
	}
	
	// create a theme
	@PostMapping(path="/api/loggedin/themes", consumes= {"multipart/form-data"})
	public ResponseEntity<Object> createTheme(@ModelAttribute Theme theme, HttpServletRequest httpServletRequest, @RequestParam(name = "files", required = false) MultipartFile[] multipartFiles, @ModelAttribute("categoryId") long categoryId) throws IOException, SQLException {
		long userPrivilege = usersUtil.getPrivilegeIdFromRequest(httpServletRequest);
		if(userPrivilege == 1)
		{
			Category category = themeService.getCategoryById(categoryId);
			Users user = userService.getUserByUserId((int) httpServletRequest.getAttribute("userId"));
			theme.setCategory(category);
			theme.setUser(user);
			Theme t = themeService.createTheme(theme, multipartFiles);
			return ResponseEntity.status(HttpStatus.OK).body(t);
			
		}
		else {
			throw new UnauthorizedException("You are not Authorized to create a Theme");
		}
		
		
	}
	
	@GetMapping(path="/api/loggedin/themes/{themeId}/download_file/{themeFileId}")
	public ResponseEntity<Object> downloadThemeFile(@PathVariable("themeId") long themeId, @PathVariable("themeFileId") long themeFileId) throws IOException {
		ThemeFiles themeFileDetails = themeService.getThemeFile(themeId, themeFileId);
		if(themeFileDetails == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File Not Found");
		}
		String filePath = ".\\data\\Themes\\";
		String fileName = themeFileDetails.getFileName();
		byte[] content = filesUtil.getFileBytes(filePath + fileName);
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(themeFileDetails.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + fileName.substring(fileName.lastIndexOf('_')) + "\"")
				.body(new ByteArrayResource(content));
	}
	
	// create an idea
	@PostMapping(path="/api/loggedin/themes/{themeId}/ideas")
	public ResponseEntity<Object> createIdea(@ModelAttribute Idea idea, @PathVariable long themeId, HttpServletRequest httpServletRequest, @RequestParam(name="files", required = false) MultipartFile[] multipartFiles) throws NotFoundException, IOException, SQLException
	{
		long userPrivilege = usersUtil.getPrivilegeIdFromRequest(httpServletRequest);
		if(userPrivilege == 2)
		{
			Users user = userService.getUserByUserId((int) httpServletRequest.getAttribute("userId"));
			idea.setUser(user);
			Idea i = ideaService.createIdea(themeId, idea, multipartFiles);
			if(i != null)
				return ResponseEntity.status(HttpStatus.OK).body(i);
			else
				throw new NotFoundException("404: Theme Not Found! Could not submit Idea");
		}
		else
		{
			throw new UnauthorizedException("You are not Authorized to create an Idea");
		}
	}
	
	

}
