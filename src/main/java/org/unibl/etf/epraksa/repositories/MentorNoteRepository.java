package org.unibl.etf.epraksa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.unibl.etf.epraksa.model.entities.MentorNote;

import java.util.List;

public interface MentorNoteRepository extends JpaRepository<MentorNote,Long>
{
    @Query("SELECT note FROM MentorNote mn WHERE" +
            "mn.mentor.Id =:mentorId")
    List<MentorNote> getNotes(Long mentorId);
}
