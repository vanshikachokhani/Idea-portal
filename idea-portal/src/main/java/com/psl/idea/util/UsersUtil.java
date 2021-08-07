package com.psl.idea.util;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.psl.idea.Constants;
import com.psl.idea.models.Users;
import com.psl.idea.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class UsersUtil {
	
	@Autowired
	private UserService userService;
	
	public long getPrivilegeIdFromRequest(HttpServletRequest httpServletRequest) {
		return userService.getUserByUserId((int) httpServletRequest.getAttribute("userId")).getPrivilege().getPrivilegeId();
	}

	//creates JWT token
	public Map<String,Object> generateJWTToken(Users user){
		long timestamp = System.currentTimeMillis();
		String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_KEY)
				.setIssuedAt(new Date(timestamp))
				.setExpiration(new Date(timestamp + Constants.TOKEN_VALIDAITY))
				.claim("userId", user.getUserId())
				.claim("emailId", user.getEmailId())
				.claim("name",user.getName())
				.claim("privilege", user.getPrivilege())
				.claim("company", user.getCompany())
				.compact();
		
		Map<String, Object> map = new HashMap<>();
		map.put("token", token);
		map.put("user", user);
		return map;
	}
}
