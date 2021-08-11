package com.psl.idea.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.psl.idea.exceptions.NotFoundException;
import com.psl.idea.models.Category;
import com.psl.idea.models.Idea;
import com.psl.idea.models.Privilege;
import com.psl.idea.models.Rating;
import com.psl.idea.models.Theme;
import com.psl.idea.models.Users;
import com.psl.idea.repository.IdeaRepo;
import com.psl.idea.repository.RatingRepo;

@WebMvcTest(controllers=RatingService.class)
@ExtendWith(SpringExtension.class)
class RatingServiceTest {

	@MockBean
	private RatingRepo ratingRepo;
	
	@MockBean
	private IdeaRepo ideaRepo;
	
	@Autowired
	private RatingService ratingService;
	
	@Test
     public void viewRatingTest(){
		int i=1;
		long id=i;
		Users user=new Users(id,"bharat","9999999","fh@ok.com","fdh@Q1","fn",new Privilege(1,"pm"));
		Users u1=new Users(id,"bharath","99999999","fhh@ok.com","fdhh@Q1","fn",new Privilege(1,"pm"));
		Idea idea=new Idea("ok ok","dk jn knd",1.0f,new Theme("om","ok ok",new Category(1,"om")),new Users(id,"bharat","9999999","fh@ok.com","fdh@Q1","fn",new Privilege(1,"pm")));
		Optional<Idea> ideaoptional=Optional.of(idea);
		when(ideaRepo.findById(id)).thenReturn(ideaoptional);
		when(ratingRepo.findByIdeaIdeaId(id)).thenReturn(Stream.of(new Rating(user, idea, false),new Rating(u1, idea, false)).collect(Collectors.toList()));
		assertEquals(2,ratingService.viewRating(id).size());
	}
	
	@Test
	public void doLikeTest() throws NotFoundException {
		int i=1;
		long id=i;
		Users user=new Users(id,"bharat","9999999","fh@ok.com","fdh@Q1","fn",new Privilege(1,"pm"));
		Idea idea=new Idea("ok ok","dk jn knd",1.0f,new Theme("om","ok ok",new Category(1,"om")),new Users(id,"bharat","9999999","fh@ok.com","fdh@Q1","fn",new Privilege(1,"pm")));
		Rating rate= new Rating(user,idea,true);
		Optional<Idea> ideaoptional=Optional.of(idea);
		when(ideaRepo.findById(id)).thenReturn(ideaoptional);
		when(ideaRepo.findByIdeaId(id)).thenReturn(idea);
		rate.setIdea(idea);
		ratingService.doLike(id, rate);
		verify(ratingRepo,times(1)).save(rate);
		assertEquals(rate.getIdea(), idea);
		
		
	}

}
