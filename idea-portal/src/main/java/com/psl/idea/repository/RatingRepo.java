package com.psl.idea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psl.idea.models.Comment;
import com.psl.idea.models.Rating;
import com.psl.idea.models.RatingId;

public interface RatingRepo extends JpaRepository <Rating, RatingId> {
	
	public List<Rating> findByIdeaIdeaId(long idea_id);

}
