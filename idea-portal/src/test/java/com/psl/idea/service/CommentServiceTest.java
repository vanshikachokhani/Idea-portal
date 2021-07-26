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
import com.psl.idea.models.Comment;
import com.psl.idea.models.Idea;
import com.psl.idea.models.Privilege;
import com.psl.idea.models.Theme;
import com.psl.idea.models.Users;
import com.psl.idea.repository.CommentRepo;
import com.psl.idea.repository.IdeaRepo;

@WebMvcTest(controllers=CommentService.class)
@ExtendWith(SpringExtension.class)
class CommentServiceTest {
	
	@MockBean
	private CommentRepo commentRepo;
	
	@MockBean
	private IdeaRepo ideaRepo;
	
	@Autowired
	private CommentService commentService;
	
	@Test
	public void viewCommentsTest(){
		int i=1;
		long id=i;
		Privilege cpPrivilege = new Privilege(1, "Client Partner");
		Category category = new Category(1, "WebApp");
		Users user = new Users(1, "Bharat", "8830850789", "bk@persistent.com", "bk", "Persistent", cpPrivilege);
		Theme theme = new Theme(1, "Test Theme", "Testing Theme", category, user);
		Idea idea=new Idea("ok", "acha", 0.0f, theme, user);
		Comment comment=new Comment("this is nice",user,idea);
		Optional<Idea> ideaoptional=Optional.of(idea);
		when(ideaRepo.findById(id)).thenReturn(ideaoptional);
		when(commentRepo.findByIdeaIdeaId(id)).thenReturn(Stream.of(comment).collect(Collectors.toList()));
		assertEquals(1, commentService.viewComments(id).size());
	}
	
	@Test
	public void createCommentTest() {
		int i=1;
		long id=i;
		Privilege cpPrivilege = new Privilege(1, "Client Partner");
		Category category = new Category(1, "WebApp");
		Users user = new Users(1, "Bharat", "8830850789", "bk@persistent.com", "bk", "Persistent", cpPrivilege);
		Theme theme = new Theme(1, "Test Theme", "Testing Theme", category, user);
		Idea idea=new Idea("ok", "acha", 0.0f, theme, user);
		Comment comment=new Comment("this is nice",user,idea);
		Optional<Idea> ideaoptional=Optional.of(idea);
		when(ideaRepo.findById(id)).thenReturn(ideaoptional);
		when(ideaRepo.findByIdeaId(id)).thenReturn(idea);
		comment.setIdea(idea);
		commentService.createComment(comment, id);
		verify(commentRepo,times(1)).save(comment);
		assertEquals(comment.getIdea(), idea);
		
	}
}
