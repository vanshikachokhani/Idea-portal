package com.psl.idea.repository;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.psl.idea.exceptions.AuthException;
import com.psl.idea.models.Users;

@Repository
public interface UserRepo extends JpaRepository<Users, Serializable> {


	public List<Users> findByemailId(String email_id);
	
	public List<Users> findByuserId (String user_id);
	
	public Users findByemailIdAndPassword(String email_id, String password);
}
