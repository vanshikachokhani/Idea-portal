package com.psl.idea;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.psl.idea.models.Category;
import com.psl.idea.models.Idea;
import com.psl.idea.models.Privilege;
import com.psl.idea.models.Theme;
import com.psl.idea.models.Users;
import com.psl.idea.repository.IdeaRepo;
import com.psl.idea.repository.IdeaRepoImpl;
import com.psl.idea.repository.ThemeRepo;
import com.psl.idea.service.IdeaService;

class TestIdeaRepoImpl {

//	@MockBean
//	IdeaRepo ideaRepo;
//	//@Autowired
//	Idea idea;
//	@Autowired
//	ThemeRepo themeRepo;
//	@Autowired
//	Users user;
//   // @Autowired
//	Privilege p;
//	@Autowired
//	IdeaRepoImpl ideaRepoImpl;
//	//@Autowired 
//	Theme t;
//	
//	@Test
//	@Transactional
//	@Rollback(true)
//	public void testfindbyUserUserId() {
//		
//		p=new Privilege();
//		p.setPrivilegeId((long)2);
//		p.setPrivilege("ProductManager");
//		
//		user =new Users(1001,"name","phoneNumber","emailId","password","company",null);	
////		user.setUserId(10001);
////		user.setCompany("company");
////		user.setEmailId("email");
////		user.setName("name");
////		user.setPassword("qwerty");
////		user.setPhoneNumber("1234567890");
////		user.setPrivilege(p);
////		
//		Theme t = new Theme(1001,"title","description",null,user) ;
//		
//		idea =new Idea(10001,"title", "description",0.0f,t, user);
//		idea.setIdeaId(10001);
//	//	ideaRepo.save(idea);
//		Mockito.when(ideaRepo.save(idea)).thenReturn(idea);
//		List<Idea> tempIdea=ideaRepoImpl.findbyUserUserId(1001);
//		Assert.assertEquals(tempIdea.get(0),idea);
//		
//	}

//	@Test
//	@Transactional
//	@Rollback(true)
//	public void testfindbylike() {
//		
//		
//	}
//	
//	@Test
//	@Transactional
//	@Rollback(true)
//	public void testfindbydate() {
//		
//		
//	}
//	
//	@Test
//	@Transactional
//	@Rollback(true)
//	public void testfindbycomment() {
//		
//		
//	}
//	
//	@Test
//	@Transactional
//	@Rollback(true)
//	public void testfindAll() {
//		
//		
//	}
//	
//	
}
