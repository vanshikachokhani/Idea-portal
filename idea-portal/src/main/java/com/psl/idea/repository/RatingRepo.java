package com.psl.idea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.psl.idea.models.Rating;
import com.psl.idea.models.RatingId;

@Repository
public interface RatingRepo extends JpaRepository <Rating, RatingId> {
	
	public List<Rating> findByIdeaIdeaId(long ideaId);
	
	@Query(value = "SELECT COALESCE(sum(CASE WHEN rating THEN 1 ELSE 0 END),0) FROM ratings", nativeQuery = true)
	int findTruevalue();
	
	@Query(value = "SELECT COUNT(*) FROM ratings", nativeQuery = true)
	int findTotal();

}
