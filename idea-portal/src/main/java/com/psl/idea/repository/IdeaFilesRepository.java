package com.psl.idea.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psl.idea.models.IdeaFiles;

public interface IdeaFilesRepository extends JpaRepository<IdeaFiles, Long> {

}
