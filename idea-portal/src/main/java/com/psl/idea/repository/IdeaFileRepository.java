package com.psl.idea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.idea.models.IdeaFiles;

@Repository
public interface IdeaFileRepository extends JpaRepository<IdeaFiles, Long> {

}
