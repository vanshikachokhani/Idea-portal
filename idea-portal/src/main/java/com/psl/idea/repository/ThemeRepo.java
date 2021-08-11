package com.psl.idea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.idea.models.Theme;

@Repository
public interface ThemeRepo extends JpaRepository<Theme, Long> {

	public List<Theme> findByUserUserId(long userId);

}
