package org.unibl.etf.epraksa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.unibl.etf.epraksa.model.entities.MentorNote;

import java.util.List;

public interface MentorNoteRepository extends JpaRepository<MentorNote,Long>
{
    @Query("SELECT shi.mentorNote FROM StudentHasInternship shi "+
            "WHERE shi.id.id = :studentId AND shi.id.internshipId = :internshipId")
    List<MentorNote> getNotes(Long studentId, Long internshipId);
}
