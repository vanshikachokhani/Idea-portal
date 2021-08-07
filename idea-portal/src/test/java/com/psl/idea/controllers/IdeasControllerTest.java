package com.psl.idea.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.psl.idea.Constants;
import com.psl.idea.models.Category;
import com.psl.idea.models.Comment;
import com.psl.idea.models.Idea;
import com.psl.idea.models.IdeaFiles;
import com.psl.idea.models.Participants;
import com.psl.idea.models.Privilege;
import com.psl.idea.models.Rating;
import com.psl.idea.models.Roles;
import com.psl.idea.models.Theme;
import com.psl.idea.models.Users;
import com.psl.idea.service.CommentService;
import com.psl.idea.service.IdeaService;
import com.psl.idea.service.ParticipantService;
import com.psl.idea.service.RatingService;
import com.psl.idea.util.FilesUtil;
import com.psl.idea.util.UsersUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@WebMvcTest(controllers=IdeasController.class)
@ExtendWith(SpringExtension.class)
class IdeasControllerTest {
	
	

	@MockBean
	private ParticipantService participantService;
	@MockBean
	private RatingService ratingService;
	@MockBean
	private CommentService commentService;
	@MockBean
	private IdeaService ideaService;
	@MockBean
	UsersUtil usersUtil;
	@MockBean
	FilesUtil filesUtil;
	
	
	@Autowired
	private MockMvc mockMvc;
	
	private Privilege cpPrivilege = new Privilege(1, "Client Partner");
	private Privilege pmPrivilege = new Privilege(2, "Product Manager");
	private Privilege pPrivilege = new Privilege(3, "Participant");
	private Category webapp = new Category(1, "WebApp");
	private Roles role=new Roles(1,"ui team");
	private Users user1 = new Users(1, "bk", "9830850720", "okok@gmail.com", "$2a$10$TdFsgRMeUDN3IEuKuryH4etBNpN7hY.iWMi83gzjskNMSLUQw7jJe", "Persistent", cpPrivilege);
	private Users user2 = new Users(2, "Bharat", "7830850720", "bkc@gmail.com", "$2a$10$TdFsgRMeUDN3IEuKuryH4etBNpN7hY.iWMi83gzjskNMSLUQw7jJe", "Persistent", pmPrivilege);
	private Users user3 = new Users(3, "Bharati", "9730850720", "bki@gmail.com", "$2a$10$TdFsgRMeUDN3IEuKuryH4etBNpN7hY.iWMi83gzjskNMSLUQw7jJe", "Persistent", pPrivilege);
	private Theme t = new Theme(1, "Test Theme", "Testing Theme", webapp, user1);
	private Idea i = new Idea(1, "Test Idea", "Testing Ideas", 0, t, user2);
	private Comment comment = new Comment("you are good", user2, i);
	private IdeaFiles ideaFile = new IdeaFiles("1.1_Test.txt", "application/text", i);
	
	private String generateJWTToken(Users user){
		long timestamp = System.currentTimeMillis();
		String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_KEY)
				.setIssuedAt(new Date(timestamp))
				.setExpiration(new Date(timestamp + Constants.TOKEN_VALIDAITY))
				.claim("userId", user.getUserId())
				.claim("emailId", user.getEmailId())
				.claim("name",user.getName())
				.claim("privilege", user.getPrivilege())
				.compact();
		return token;
	}
	
	
	@Test
	void viewIdeaTest() throws Exception {
		long id=1L;
		when(participantService.viewIdea(id)).thenReturn(i);
		when(participantService.viewIdea((long) 0)).thenReturn((Idea) null);
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/ideas/{ideaId}/viewIdea",id).header("Authorization", "Bearer " + this.generateJWTToken(user2)))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/ideas/0/viewIdea")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
		
	}
	
	@Test
	void likeDislikeTest() throws Exception {
		long id=1L;
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/loggedin/ideas/{ideaId}/like",id).header("Authorization", "Bearer " + this.generateJWTToken(user2)).contentType(MediaType.APPLICATION_JSON).content("{\"user\":{\"userId\": 2},\"idea\":{\"ideaId\":1},\"rating\":true}"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void createCommentTest() throws Exception {
		when(commentService.createComment(any(Comment.class), any(Long.class))).thenReturn(comment, (Comment) null);
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/loggedin/ideas/1/comment")
				.header("Authorization", "Bearer " + this.generateJWTToken(user2))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"comment\":\"you are good\",\"user\":{\"userId\": 2},\"idea\":{\"ideaId\":1}}"))
		.andExpect(MockMvcResultMatchers.status().isOk());
		

		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/loggedin/ideas/2/comment")
				.header("Authorization", "Bearer " + this.generateJWTToken(user2))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"comment\":\"you are good\",\"user\":{\"userId\": 2},\"idea\":{\"ideaId\":2}}"))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	void viewRatingTest() throws Exception {
		long id=1L;
		when(ratingService.viewRating(id)).thenReturn(Stream.of(new Rating(user2, i, false),new Rating(user1, i, true)).collect(Collectors.toList()));
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/loggedin/ideas/{ideaId}/viewLike",id).header("Authorization", "Bearer " + this.generateJWTToken(user2)))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void viewCommentTest() throws Exception {
		long id=1L;
		Comment comment=new Comment("this is nice",user2,i);
		when(commentService.viewComments(id)).thenReturn(Stream.of(comment).collect(Collectors.toList()));
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/loggedin/ideas/{ideaId}/viewComments",id).header("Authorization", "Bearer " + this.generateJWTToken(user2)))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void interestedParticipantTest() throws Exception {
		long id=1L;
		when(usersUtil.getPrivilegeIdFromRequest(any())).thenReturn(user3.getPrivilege().getPrivilegeId());
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/loggedin/ideas/{ideaId}/interested",id).header("Authorization", "Bearer " + this.generateJWTToken(user3)).contentType(MediaType.APPLICATION_JSON).content("{\"user\":{\"userId\": 3},\"idea\":{\"ideaId\":1},\"role\":{\"roleId\":1}}"))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
		when(usersUtil.getPrivilegeIdFromRequest(any())).thenReturn(user2.getPrivilege().getPrivilegeId());
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/loggedin/ideas/{ideaId}/interested",id).header("Authorization", "Bearer " + this.generateJWTToken(user2)).contentType(MediaType.APPLICATION_JSON).content("{\"user\":{\"userId\": 2},\"idea\":{\"ideaId\":1},\"role\":{\"roleId\":1}}"))
		.andExpect(MockMvcResultMatchers.status().isUnauthorized());
		
	}
	
	@Test
	void viewInterestedParticipantsTest() throws Exception {
		long id=1L;
		Participants p=new Participants(user1, i, role);
		Participants p1=new Participants(user2, i, role);
		when(participantService.viewInterested(id)).thenReturn(Stream.of(p).collect(Collectors.toList()));
		when(usersUtil.getPrivilegeIdFromRequest(any())).thenReturn(user1.getPrivilege().getPrivilegeId());
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/loggedin/ideas/{ideaId}/viewInterested",id).header("Authorization", "Bearer " + this.generateJWTToken(user1)))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
		when(participantService.viewInterested(id)).thenReturn(Stream.of(p1).collect(Collectors.toList()));
		when(usersUtil.getPrivilegeIdFromRequest(any())).thenReturn(user2.getPrivilege().getPrivilegeId());
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/loggedin/ideas/{ideaId}/viewInterested",id).header("Authorization", "Bearer " + this.generateJWTToken(user1)))
		.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}
	
	@Test
	public void downloadIdeaFileTest() throws Exception {
		when(ideaService.getIdeaFile(any(Long.class), any(Long.class))).thenReturn((IdeaFiles) null, ideaFile);
		when(filesUtil.getFileBytes(any(String.class))).thenReturn(new byte[100]);
		
		mockMvc.perform(get("/api/loggedin/ideas/0/download_file/0")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + this.generateJWTToken(user1)))
		.andExpect(status().isNotFound());

		mockMvc.perform(get("/api/loggedin/ideas/1/download_file/1")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + this.generateJWTToken(user1)))
		.andExpect(status().isOk());
	}

}
