package com.psl.idea.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.psl.idea.models.Category;
import com.psl.idea.models.Comment;
import com.psl.idea.models.Idea;
import com.psl.idea.models.Privilege;
import com.psl.idea.models.Theme;
import com.psl.idea.models.Users;
import com.psl.idea.repository.CommentRepo;
import com.psl.idea.repository.IdeaRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
class CommentServiceTest {
	
	@MockBean
	private CommentRepo commentRepo;
	
	@MockBean
	private IdeaRepo ideaRepo;
	
	@Autowired
	private CommentService commentService;
	
	@Test
	public void viewCommentsTest() {
		int i=1;
		long id=i;
		Idea idea=new Idea("ok ok","dk jn knd",null,1.0f,new Theme("om","ok ok",new Category(1,"om"),null),new Users("bharat","9999999","fh@ok.com","fdh@Q1",new Privilege(1,"pm")));
		Optional<Idea> ideaoptional=Optional.of(idea);
		when(ideaRepo.findById(id)).thenReturn(ideaoptional);
		when(commentRepo.findByIdeaIdeaId(id)).thenReturn(Stream.of(new Comment("I love this idea",new Users("bharat","9999999","fh@ok.com","fdh@Q1",new Privilege(1,"pm")),new Idea("ok ok","dk jn knd",null,1.0f,new Theme("om","ok ok",new Category(1,"om"),null),new Users("bharat","9999999","fh@ok.com","fdh@Q1",new Privilege(1,"pm")))),new Comment("you can work on this",new Users("bharat","9999999","fh@ok.com","fdh@Q1",new Privilege(1,"pm")),new Idea("ok ok","dk jn knd",null,1.0f,new Theme("om","ok ok",new Category(1,"om"),null),new Users("bharat","9999999","fh@ok.com","fdh@Q1",new Privilege(1,"pm"))))).collect(Collectors.toList()));
		assertEquals(2,commentService.viewComments(id).size());	
	}
	
	@Test
	public void createCommentTest() {
		int i=1;
		long id=i;
		Comment comment=new Comment("I love this idea",new Users("bharat","9999999","fh@ok.com","fdh@Q1",new Privilege(1,"pm")),new Idea("ok ok","dk jn knd",null,1.0f,new Theme("om","ok ok",new Category(1,"om"),null),new Users("bharat","9999999","fh@ok.com","fdh@Q1",new Privilege(1,"pm"))));
		Idea idea=new Idea("ok ok","dk jn knd",null,1.0f,new Theme("om","ok ok",new Category(1,"om"),null),new Users("bharat","9999999","fh@ok.com","fdh@Q1",new Privilege(1,"pm")));
		Optional<Idea> ideaoptional=Optional.of(idea);
		when(ideaRepo.findById(id)).thenReturn(ideaoptional);
		when(ideaRepo.findByIdeaId(id)).thenReturn(idea);
		comment.setIdea(idea);
		commentService.createComment(comment, id);
		verify(commentRepo,times(1)).save(comment);
		assertEquals(comment.getIdea(), idea);
			}
}
