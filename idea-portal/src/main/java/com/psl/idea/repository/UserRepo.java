package com.psl.idea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.idea.models.Users;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {


	public Users findByEmailId(String emailId);
	
	public Users findByuserId (long userId);
	
	public Users findByemailIdAndCompany(String emailId, String company);
	
	public Users findByemailIdAndPassword(String emailId, String password);
}
