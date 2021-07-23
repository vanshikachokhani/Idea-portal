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
import com.psl.idea.models.Category;
import com.psl.idea.models.Idea;
import com.psl.idea.models.Participants;
import com.psl.idea.models.Privilege;
import com.psl.idea.models.Roles;
import com.psl.idea.models.Theme;
import com.psl.idea.models.Users;
import com.psl.idea.repository.IdeaRepo;
import com.psl.idea.repository.ParticipantRepo;

@WebMvcTest(controllers=ParticipantService.class)
@ExtendWith(SpringExtension.class)
class ParticipantServiceTest {
	
	@MockBean
	private ParticipantRepo participantRepo;
	
	@MockBean
	private IdeaRepo ideaRepo;
	
	@Autowired
	private ParticipantService participantService;
	
	@Test
	public void viewIdeaTest() {
		int i=1;
		long id=i;
		Idea idea=new Idea("ok ok","dk jn knd",null,1.0f,new Theme("om","ok ok",new Category(1,"om"),null),new Users(id,"bharat","9999999","fh@ok.com","fdh@Q1","gg",new Privilege(1,"pm")));
		Optional<Idea> ideaoptional=Optional.of(idea);
		when(ideaRepo.findById(id)).thenReturn(ideaoptional);
		when(ideaRepo.getById(id)).thenReturn(idea);
		assertEquals(participantService.viewIdea(id), idea);
		
	}
	
	@Test
	public void viewInterestedTest(){
		int i=1;
		long id=i;
		Users user=new Users(id,"bharat","9999999","fh@ok.com","fdh@Q1","bg", new Privilege(1,"pm"));
		Users u1=new Users(id,"bharath","99999999","fhh@ok.com","fdhh@Q1","vv",new Privilege(1,"pm"));
		Roles role=new Roles(1,"dev");
		Roles role1=new Roles(2,"dev1");
		Idea idea=new Idea("ok ok","dk jn knd",null,1.0f,new Theme("om","ok ok",new Category(1,"om"),null),new Users(id,"bharat","9999999","fh@ok.com","fdh@Q1","bg",new Privilege(1,"pm")));
		Participants p=new Participants(user, idea, role1);
		Participants p1=new Participants(u1, idea, role);
		Optional<Idea> ideaoptional=Optional.of(idea);
		when(ideaRepo.findById(id)).thenReturn(ideaoptional);
		when(participantRepo.findByIdeaIdeaId(id)).thenReturn(Stream.of(p,p1).collect(Collectors.toList()));
		assertEquals(2,participantService.viewInterested(id).size());
		
	}
	
	@Test
	public void interestInTest(){
		int i=1;
		long id=i;
		Users user=new Users(id,"bharat","9999999","fh@ok.com","fdh@Q1","fg",new Privilege(1,"pm"));
		Roles role1=new Roles(2,"dev1");
		Idea idea=new Idea("ok ok","dk jn knd",null,1.0f,new Theme("om","ok ok",new Category(1,"om"),null),new Users(id,"bharat","9999999","fh@ok.com","fdh@Q1","ggg",new Privilege(1,"pm")));
		Participants p=new Participants(user, idea, role1);
		Optional<Idea> ideaoptional=Optional.of(idea);
		when(ideaRepo.findById(id)).thenReturn(ideaoptional);
		when(ideaRepo.findByIdeaId(id)).thenReturn(idea);
		p.setIdea(idea);
		participantService.interestIn(p, id);
		verify(participantRepo,times(1)).save(p);
		assertEquals(p.getIdea(), idea);
	}
	

	
}
