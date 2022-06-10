package org.unibl.etf.epraksa.services;

import org.unibl.etf.epraksa.model.dataTransferObjects.CommissionMemberNoteDTO;

import java.util.List;

public interface CommissionMemberNoteService
{
    <T> List<T> getNotes(Long studentId, Long internshipId, Class<T> replyClass);
    <T> T insertNote(CommissionMemberNoteDTO newNote, Class<T> replyClass);
    void deleteNote(Long noteId);
}
