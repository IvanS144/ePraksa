package org.unibl.etf.epraksa.model.dataTransferObjects;

import lombok.Data;

@Data
public class MentorNoteDTO
{
    Long id;
    String text;
    Long mentorId;
    Long studentId;
    Long internshipId;
}
