package org.unibl.etf.epraksa.model.dataTransferObjects;

import lombok.Data;

@Data
public class CommissionMemberNoteDTO
{
    Long id;
    Long commissionMemberId;
    String text;
    Long studentId;
    Long internshipId;
}
