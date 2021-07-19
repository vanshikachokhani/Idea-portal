package com.psl.idea.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psl.idea.models.Comment;
import com.psl.idea.models.Idea;
import com.psl.idea.models.Participants;
import com.psl.idea.models.Rating;
import com.psl.idea.models.Users;
import com.psl.idea.service.CommentService;
import com.psl.idea.service.IdeaService;
import com.psl.idea.service.ParticipantService;
import com.psl.idea.service.RatingService;

@RestController
@RequestMapping("/ideas")
public class IdeasController {
	
	@Autowired
	CommentService commentservice;
	
	@Autowired
	ParticipantService participantservice;
	
	@Autowired
	IdeaService ideaservice;
	
	@Autowired
	RatingService ratingservice;
	
	@GetMapping(path="/")
	public Idea viewIdea(Users user) {
		Long l=(Long) user.getUserId();
		return participantservice.viewIdea(l);
	}
	
	//like or dislike an idea
	@PostMapping(path="/like")
	public void likeDislike(@RequestBody Rating r) {
		ratingservice.doLike(r);
	}
	
	//do comment
	@PostMapping(path="/comment")
	public void createComment(@RequestBody Comment comment) {
		commentservice.createComment(comment);
	}
	// showing all ratings
	@GetMapping(path="/viewLike")
	public List<Rating> viewRating(){
		return ratingservice.viewRating();
	}
	
	//showing comments
	@GetMapping(path="/viewcomment")
	public List<Comment> viewComment(){
		return commentservice.viewComments();
	}
	
	//interest in indea
	@PostMapping(path="/Interested")
	public void interestedParticipant(@RequestBody Participants participants) {
		participantservice.interestIn(participants);
	}
	
	// see all interested participants in this idea
	@GetMapping(path="/viewInterested")
	public List<Participants> viewInterestedParticipants(){
		return participantservice.viewInterested();
	}
	
	// create an Idea
	@PostMapping(path="/")
	public void createIdea(@RequestBody Idea idea)
	{
		System.out.println("Post Request CreateIdea");
		System.out.println(idea);
		participantservice.createIdea(idea);
	}

}
