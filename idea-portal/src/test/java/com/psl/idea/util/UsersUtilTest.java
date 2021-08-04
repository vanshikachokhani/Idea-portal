package com.psl.idea.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.psl.idea.models.Privilege;
import com.psl.idea.models.Users;
import com.psl.idea.service.UserService;

@WebMvcTest(controllers = UsersUtil.class)
@ExtendWith(SpringExtension.class)
public class UsersUtilTest {

	@MockBean
	UserService userService;
	@MockBean
	HttpServletRequest httpServletRequest;
	
	@Autowired
	UsersUtil usersUtil;
	@Autowired
	MockMvc mockMvc;
	
	Privilege cpPrivilege = new Privilege(1, "Client Partner");
	Users user = new Users(1, "Test User", "1234567890", "test@example.com", "password", "Persistent", cpPrivilege);
	
	@Test
	public void getPrivilegeIdFromRequestTest() {
		when(httpServletRequest.getAttribute("userId")).thenReturn((Object) 1);
		when(userService.getUserByUserId((long) 1)).thenReturn(user);
		
		assertEquals(1, usersUtil.getPrivilegeIdFromRequest(httpServletRequest));
	}
	
}
