package com.psl.idea.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psl.idea.models.Idea;

public interface IdeaRepo extends JpaRepository<Idea, Long> {


}
