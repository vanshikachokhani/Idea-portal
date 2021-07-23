package com.psl.idea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psl.idea.models.Participants;
import com.psl.idea.models.ParticipantsId;

public interface ParticipantRepo extends JpaRepository <Participants, ParticipantsId>{
	
	public List<Participants> findByIdeaIdeaId(long idea_id);

}
