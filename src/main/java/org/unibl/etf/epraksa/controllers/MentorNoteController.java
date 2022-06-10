package org.unibl.etf.epraksa.controllers;

import org.springframework.web.bind.annotation.*;
import org.unibl.etf.epraksa.model.dataTransferObjects.MentorNoteDTO;
import org.unibl.etf.epraksa.services.MentorNoteService;

import java.util.List;

@RestController
@RequestMapping("/mentornotes")
public class MentorNoteController
{
    private final MentorNoteService mentorNoteService;

    public MentorNoteController(MentorNoteService mentorNoteService)
    {
        this.mentorNoteService = mentorNoteService;
    }

    @GetMapping("/{studentId}/{internshipId}")
    public List<MentorNoteDTO> getNotes(@PathVariable Long studentId,
                                        @PathVariable Long internshipId)
    {
        return mentorNoteService.getNotes(studentId, internshipId, MentorNoteDTO.class);
    }

    @DeleteMapping("/{noteId}")
    public void deleteNote(@PathVariable Long noteId)
    {
        mentorNoteService.deleteNote(noteId);
    }

    @PostMapping
    public MentorNoteDTO insertNewNote(@RequestBody MentorNoteDTO newNote)
    {
        return mentorNoteService.insertNote(newNote,MentorNoteDTO.class);
    }
}
