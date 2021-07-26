package com.psl.idea.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psl.idea.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
