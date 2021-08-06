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

import com.psl.idea.exceptions.NotFoundException;
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

@RestController
@RequestMapping("/api")
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
	
	@GetMapping(path="/ideas/{ideaId}/viewIdea")
	public ResponseEntity<Idea> viewIdea(@PathVariable Long ideaId) throws NotFoundException {
		Idea idea = participantService.viewIdea(ideaId);
		if(idea == null)
			throw new NotFoundException("Idea Not Found!");
		return new ResponseEntity<>(idea, HttpStatus.OK);
	}
	
	//like or dislike an idea
	@PostMapping(path="/loggedin/ideas/{ideaId}/like")
	public ResponseEntity<Object> likeDislike(@PathVariable long ideaId,@RequestBody Rating rate) {
		ratingService.doLike(ideaId,rate);
		return ResponseEntity.status(HttpStatus.OK).body("inserted");
		
		
	}
	
	//do comment
	@PostMapping(path="/loggedin/ideas/{ideaId}/comment")
	public ResponseEntity<Comment> createComment(@RequestBody Comment comment,@PathVariable Long ideaId) throws NotFoundException {
		comment = commentService.createComment(comment,ideaId);
		if(comment != null)
			return new ResponseEntity<>(comment, HttpStatus.OK);
		throw new NotFoundException("Idea Not Found");
	}
	
	// showing all ratings
	@GetMapping(path="/loggedin/ideas/{ideaId}/viewLike")
	public List<Rating> viewRating(@PathVariable Long ideaId){
		return ratingService.viewRating(ideaId);
	}
	
	//showing comments
	@GetMapping(path="/loggedin/ideas/{ideaId}/viewComments")
	public List<Comment> viewComment(@PathVariable Long ideaId){
		return commentService.viewComments(ideaId);
	}
	
	//interest in idea
	@PostMapping(path="/loggedin/ideas/{ideaId}/interested")
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
	@GetMapping(path="/loggedin/ideas/{ideaId}/viewInterested")
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
	
	@GetMapping(path="/loggedin/ideas/{ideaId}/download_file/{ideaFileId}")
	public ResponseEntity<Object> downloadIdeaFile(@PathVariable("ideaId") long ideaId, @PathVariable("ideaFileId") long ideaFileId) throws IOException {
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
