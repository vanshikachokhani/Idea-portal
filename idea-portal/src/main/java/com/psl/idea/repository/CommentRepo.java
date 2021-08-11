package com.psl.idea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.idea.models.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
	
	 List<Comment> findByIdeaIdeaId (long ideaId);

}
