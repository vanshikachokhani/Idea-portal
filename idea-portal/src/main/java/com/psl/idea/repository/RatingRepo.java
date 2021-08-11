package com.psl.idea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.psl.idea.models.Rating;
import com.psl.idea.models.RatingId;

@Repository
public interface RatingRepo extends JpaRepository <Rating, RatingId> {
	
	public List<Rating> findByIdeaIdeaId(long idea_id);
	
	@Query("SELECT COALESCE(sum(CASE WHEN rating THEN 1 ELSE 0 END),0) FROM ratings")
	int findTruevalue();
	
	@Query("SELECT COUNT(*) FROM ratings")
	int findTotal();

}
