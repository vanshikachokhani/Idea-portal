package com.psl.idea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.idea.models.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {

}
