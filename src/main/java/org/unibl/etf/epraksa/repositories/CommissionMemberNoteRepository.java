package org.unibl.etf.epraksa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.unibl.etf.epraksa.model.entities.CommissionMemberNote;

import java.util.List;

public interface CommissionMemberNoteRepository extends JpaRepository<CommissionMemberNote,Long>
{
    @Query("SELECT shi.commissionMemberNote FROM StudentHasInternship shi "+
            "WHERE shi.id.id = :studentId AND shi.id.internshipId = :internshipId")
    List<CommissionMemberNote> getNotes(Long studentId, Long internshipId);
}
