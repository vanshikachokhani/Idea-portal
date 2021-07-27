package com.psl.idea.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psl.idea.exceptions.UnauthorizedException;
import com.psl.idea.models.Comment;
import com.psl.idea.models.Idea;
import com.psl.idea.models.IdeaFiles;
import com.psl.idea.models.Participants;
import com.psl.idea.models.Rating;
import com.psl.idea.service.CommentService;
import com.psl.idea.service.IdeaService;
import com.psl.idea.service.ParticipantService;
import com.psl.idea.service.RatingService;
import com.psl.idea.util.UsersUtil;
import com.sun.mail.iap.Response;

@RestController
@RequestMapping("/api/loggedin/ideas/{ideaId}")
public class IdeasController {
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	ParticipantService participantService;
	
	@Autowired
	IdeaService ideaService;
	
	@Autowired
	RatingService ratingService;
	
	@Autowired
	private UsersUtil usersUtil;
	
	@GetMapping(path="/viewIdea")
	public Idea viewIdea(@PathVariable Long ideaId) {
		return participantService.viewIdea(ideaId);
	}
	
	//like or dislike an idea
	@PostMapping(path="/like")
	public ResponseEntity<Object> likeDislike(@PathVariable long ideaId,@RequestBody Rating rate) {
		ratingService.doLike(ideaId,rate);
		return ResponseEntity.status(HttpStatus.OK).body("inserted");
		
		
	}
	
	//do comment
	@PostMapping(path="/comment")
	public void createComment(@RequestBody Comment comment,@PathVariable Long ideaId) {
		commentService.createComment(comment,ideaId);
	}
	// showing all ratings
	@GetMapping(path="/viewLike")
	public List<Rating> viewRating(@PathVariable Long ideaId){
		return ratingService.viewRating(ideaId);
	}
	
	//showing comments
	@GetMapping(path="/viewComments")
	public List<Comment> viewComment(@PathVariable Long ideaId){
		return commentService.viewComments(ideaId);
	}
	
	//interest in idea
	@PostMapping(path="/interested")
	public ResponseEntity<Object> interestedParticipant(@RequestBody Participants participants, HttpServletRequest httpServletRequest,@PathVariable Long ideaId) {
		long userPrivilege = usersUtil.getPrivilegeIdFromRequest(httpServletRequest);
		if(userPrivilege == 3)
		{
		  participantService.interestIn(participants,ideaId);
		  return ResponseEntity.status(HttpStatus.OK).body("your interest created");
		}
		else
		{
			throw new UnauthorizedException("you are not Authorized to participate");
		}
	}
	
	// see all interested participants in this idea
	@GetMapping(path="/viewInterested")
	public ResponseEntity<Object> viewInterestedParticipants(HttpServletRequest httpServletRequest,@PathVariable Long ideaId){
		long userPrivilege = usersUtil.getPrivilegeIdFromRequest(httpServletRequest);
		if(userPrivilege == 1)
		{
	        	List<Participants> participant= participantService.viewInterested(ideaId);
	        	return ResponseEntity.status(HttpStatus.OK).body(participant);
		}
		else
		{
			throw new UnauthorizedException("you are not Authorized to see interested Participants");
		}
	}
	
	@GetMapping(path="/download_file/{ideaFileId}")
	public ResponseEntity<Object> downloadIdeaFile(@PathVariable("ideaId") long ideaId, @PathVariable("ideaFileId") long ideaFileId) throws IOException {
		System.out.println("Get Mapping Idea");
		IdeaFiles ideaFileDetails = ideaService.getIdeaFile(ideaId, ideaFileId);
		if(ideaFileDetails == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File Not Found");
		}
		String filePath = "E:\\Persistent\\data\\Ideas\\";
		String fileName = ideaFileDetails.getFileName();
		byte[] content = Files.readAllBytes(Paths.get(filePath + fileName));
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(ideaFileDetails.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + fileName.substring(fileName.lastIndexOf('_')+1) + "\"")
				.body(new ByteArrayResource(content));
	}

}
