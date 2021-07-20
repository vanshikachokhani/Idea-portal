package com.psl.idea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.idea.models.Users;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {


	public Users findByEmailId(String email_id);
	
	public Users findByuserId (long user_id);
	
	public Users findByemailIdAndPassword(String email_id, String password);
}
