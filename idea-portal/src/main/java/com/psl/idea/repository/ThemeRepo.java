package com.psl.idea.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psl.idea.models.Theme;

public interface ThemeRepo extends JpaRepository<Theme, Long> {

}
