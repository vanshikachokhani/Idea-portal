package com.psl.idea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psl.idea.models.Idea;

public interface IdeaRepo extends JpaRepository<Idea, Long> {

	List<Idea> findByUserUserId(long userId);

}
