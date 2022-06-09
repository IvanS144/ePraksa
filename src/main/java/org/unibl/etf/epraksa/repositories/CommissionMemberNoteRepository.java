package org.unibl.etf.epraksa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.unibl.etf.epraksa.model.entities.CommissionMemberNote;

import java.util.List;

public interface CommissionMemberNoteRepository extends JpaRepository<CommissionMemberNote,Long>
{
    @Query("SELECT cmn  FROM CommissionMemberNote cmn WHERE cmn.commissionMember.Id =:commissionMemberId")
    List<CommissionMemberNote> getNotes(Long commissionMemberId);
}
