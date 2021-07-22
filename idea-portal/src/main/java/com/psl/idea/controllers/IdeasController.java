package com.psl.idea.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psl.idea.models.Comment;
import com.psl.idea.models.Idea;
import com.psl.idea.models.Participants;
import com.psl.idea.models.Rating;
import com.psl.idea.service.CommentService;
import com.psl.idea.service.IdeaService;
import com.psl.idea.service.ParticipantService;
import com.psl.idea.service.RatingService;
import com.psl.idea.util.UsersUtil;

@RestController
@RequestMapping("/api/loggedin/{ideaId}")
public class IdeasController {
	
	@Autowired
	CommentService commentservice;
	
	@Autowired
	ParticipantService participantservice;
	
	@Autowired
	IdeaService ideaservice;
	
	@Autowired
	RatingService ratingservice;
	
	@Autowired
	private UsersUtil usersUtil;
	
	@GetMapping(path="/viewIdea")
	public Idea viewIdea(@PathVariable Long ideaId) {
		return participantservice.viewIdea(ideaId);
	}
	
	//like or dislike an idea
	@PostMapping(path="/like")
	public void likeDislike(@PathVariable long ideaId,@RequestBody Rating rate) {
		ratingservice.doLike(ideaId,rate);
	}
	
	//do comment
	@PostMapping(path="/comment")
	public void createComment(@RequestBody Comment comment,@PathVariable Long ideaId) {
		commentservice.createComment(comment,ideaId);
	}
	// showing all ratings
	@GetMapping(path="/viewLike")
	public List<Rating> viewRating(@PathVariable Long ideaId){
		return ratingservice.viewRating(ideaId);
	}
	
	//showing comments
	@GetMapping(path="/viewComments")
	public List<Comment> viewComment(@PathVariable Long ideaId){
		return commentservice.viewComments(ideaId);
	}
	
	//interest in idea
	@PostMapping(path="/Interested")
	public ResponseEntity<Object> interestedParticipant(@RequestBody Participants participants, HttpServletRequest httpServletRequest,@PathVariable Long ideaId) {
		long userPrivilege = usersUtil.getPrivilegeIdFromRequest(httpServletRequest);
		if(userPrivilege == 3)
		{
		  participantservice.interestIn(participants,ideaId);
		  return ResponseEntity.status(HttpStatus.OK).body(null);
		}
		else
		{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("401: Not Authorized");
		}
	}
	
	// see all interested participants in this idea
	@GetMapping(path="/viewInterested")
	public ResponseEntity<Object> viewInterestedParticipants(HttpServletRequest httpServletRequest,@PathVariable Long ideaId){
		long userPrivilege = usersUtil.getPrivilegeIdFromRequest(httpServletRequest);
		if(userPrivilege == 1)
		{
	        	List<Participants> participant= participantservice.viewInterested(ideaId);
	        	return ResponseEntity.status(HttpStatus.OK).body(participant);
		}
		else
		{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("401: Not Authorized");
		}
	}

}
