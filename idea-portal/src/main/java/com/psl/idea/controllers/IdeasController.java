package com.psl.idea.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.psl.idea.models.Roles;
import com.psl.idea.models.Users;
import com.psl.idea.service.CommentService;
import com.psl.idea.service.IdeaService;
import com.psl.idea.service.ParticipantService;
import com.psl.idea.service.RatingService;

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
	
	//interest in indea
	@PostMapping(path="/Interested")
	public void interestedParticipant(@RequestBody Participants participants,@PathVariable Long ideaId) {
		participantservice.interestIn(participants,ideaId);
	}
	
	// see all interested participants in this idea
	@GetMapping(path="/viewInterested")
	public List<Participants> viewInterestedParticipants(@PathVariable Long ideaId){
		return participantservice.viewInterested(ideaId);
	}

}
