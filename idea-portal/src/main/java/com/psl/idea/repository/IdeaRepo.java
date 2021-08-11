package com.psl.idea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.psl.idea.models.Idea;

@Repository
public interface IdeaRepo extends JpaRepository<Idea, Long> {

	List<Idea> findByUserUserId(long userId);
	Idea findByIdeaId(long idea_id);
	Idea[] findAllByThemeThemeId(long themeId);
	
	@Query(value ="SELECT *\r\n"
			+ "	FROM ideas I\r\n"
			+ "	LEFT OUTER JOIN comments C ON I.idea_id=C.idea_id WHERE theme_id=?1\r\n"
			+ "	GROUP BY I.idea_id ORDER BY COUNT(C.idea_id) DESC;",nativeQuery = true)
	List<Idea> findbycomment(long themeID);

}
