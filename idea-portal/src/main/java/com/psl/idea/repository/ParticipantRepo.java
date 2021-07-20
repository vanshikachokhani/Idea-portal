package com.psl.idea.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psl.idea.models.Participants;
import com.psl.idea.models.ParticipantsId;

public interface ParticipantRepo extends JpaRepository <Participants, ParticipantsId>{

}
