package com.psl.idea.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psl.idea.models.Rating;
import com.psl.idea.models.RatingId;

public interface RatingRepo extends JpaRepository <Rating, RatingId> {

}
