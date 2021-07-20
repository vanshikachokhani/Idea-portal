package com.psl.idea.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.psl.idea.service.UserService;

@Component
public class UsersUtil {
	
	@Autowired
	private UserService userService;
	
	public long getPrivilegeIdFromRequest(HttpServletRequest httpServletRequest) {
		return userService.getUserByUserId((int) httpServletRequest.getAttribute("userId")).getPrivilege().getPrivilegeId();
	}

}
