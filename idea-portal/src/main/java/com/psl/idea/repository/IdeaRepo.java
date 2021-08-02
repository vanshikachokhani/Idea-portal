package com.psl.idea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.psl.idea.models.Idea;

public interface IdeaRepo extends JpaRepository<Idea, Long> {

	List<Idea> findByUserUserId(long userId);
	Idea findByIdeaId(long idea_id);
	Idea[] findAllByThemeThemeId(long themeId);
	
	@Query(value ="FROM Idea I where I.theme.themeId=?1 LEFT OUTER JOIN FROM Comment C WHERE C.theme.themeId=?1 GROUP BY C.idea.ideaId ON I.theme=C.theme ORDER BY COUNT(C.commentId) DESC",nativeQuery = true)
	List<Idea> findbycomment(long themeID);

}
