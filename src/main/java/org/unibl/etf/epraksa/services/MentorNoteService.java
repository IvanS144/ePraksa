package org.unibl.etf.epraksa.services;

import org.unibl.etf.epraksa.model.dataTransferObjects.MentorNoteDTO;

import java.util.List;

public interface MentorNoteService
{
    <T> List<T> getNotes(Long mentorId, Class<T> replyClass);
    <T> T insertNote(MentorNoteDTO newNote, Class<T> replyClass);
    void deleteNote(Long noteId);
}
