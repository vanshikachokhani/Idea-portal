package com.psl.idea;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.psl.idea.models.Privilege;
import com.psl.idea.models.Users;
import com.psl.idea.repository.UserRepo;

class TestUserRepo {

	@Autowired
	UserRepo userRepo;

	@Autowired
	Privilege p;
	
	@Test
	public void testfindByEmailId(String email_id) {
		//fail("Not yet implemented");
		p.setPrivilege("ProductManager");
		p.setPrivilegeId(1);
		Users user=new Users("vaibhav","8423","email@gmail.com","pass",p);
		assertEquals(user, userRepo.findByEmailId(email_id));
	}

}
