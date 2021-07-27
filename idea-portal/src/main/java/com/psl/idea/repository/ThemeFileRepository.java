package com.psl.idea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.idea.models.ThemeFiles;

@Repository
public interface ThemeFileRepository extends JpaRepository<ThemeFiles, Long> {

	ThemeFiles[] findAllByThemeThemeId(long themeId);

}
